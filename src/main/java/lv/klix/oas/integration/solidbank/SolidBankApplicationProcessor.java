package lv.klix.oas.integration.solidbank;

import lombok.extern.slf4j.Slf4j;
import lv.klix.oas.conf.ProductConfig;
import lv.klix.oas.integration.ApplicationProcessor;
import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
public class SolidBankApplicationProcessor extends ApplicationProcessor {
    public static final String NAME = "SolidBank";

    private final SolidBankOfferMapper solidBankOfferMapper;
    private final WebClient solidBankWebClient;

    public SolidBankApplicationProcessor(ProductConfig productConfig, SolidBankOfferMapper solidBankOfferMapper, WebClient solidBankWebClient) {
        super(productConfig);
        this.solidBankOfferMapper = solidBankOfferMapper;
        this.solidBankWebClient = solidBankWebClient;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean isApplicable(ApplicationDTO request) {
        return true;
    }

    @Override
    public Mono<OfferDTO> process(ApplicationDTO request) {
        var solidBankApplicationRequest = solidBankOfferMapper.map(request);
        return submitApplication(solidBankApplicationRequest)
                .doOnNext(resp -> log.info("Submitted application: {}", resp))
                .flatMap(resp ->
                        findApplication(resp.getId())
                                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(2)))
                                .takeUntil(response -> response.getStatus() == SolidBankApplicationResponse.Status.PROCESSED)
                                .doOnNext(response -> log.info("Polling response: {}", response))
                                .last()
                )
                .map(applResp -> solidBankOfferMapper.map(applResp.getOffer()))
                .doOnError(err -> log.error("Error during application processing: {}", err.getMessage(), err));
    }

    public Mono<SolidBankApplicationResponse> submitApplication(SolidBankApplicationRequest request) {
        return solidBankWebClient.post()
                .uri("/applications")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SolidBankApplicationResponse.class);
    }

    public Mono<SolidBankApplicationResponse> findApplication(String applicationId) {
        return solidBankWebClient.get()
                .uri("/applications/" + applicationId)
                .retrieve()
                .bodyToMono(SolidBankApplicationResponse.class);
    }
}

