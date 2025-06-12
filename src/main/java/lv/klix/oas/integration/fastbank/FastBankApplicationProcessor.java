package lv.klix.oas.integration.fastbank;

import lombok.extern.slf4j.Slf4j;
import lv.klix.oas.conf.ProductConfig;
import lv.klix.oas.integration.ApplicationProcessor;
import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import lv.klix.oas.validator.PhoneType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
public class FastBankApplicationProcessor extends ApplicationProcessor {
    public static final String NAME = "FastBank";

    private final FastBankOfferMapper fastBankOfferMapper;
    private final WebClient fastBankWebClient;

    public FastBankApplicationProcessor(ProductConfig productConfig, FastBankOfferMapper fastBankOfferMapper, WebClient fastBankWebClient) {
        super(productConfig);
        this.fastBankOfferMapper = fastBankOfferMapper;
        this.fastBankWebClient = fastBankWebClient;
    }

    @Override
    public boolean isApplicable(ApplicationDTO request) {
        return request.getPhone().matches(PhoneType.LATVIA.getRegex());
    }

    @Override
    public String name() {
        return NAME;
    }


    /**
     Both financing institutions have similar logic in this case.
     Some attempts were made to extract common parts, which resulted in decreased code readability.
     Taking into account the fact that in real life integration providers differ, current implementation left as it is.
     */

    @Override
    public Mono<OfferDTO> process(ApplicationDTO request) {
        var fastBankApplicationRequest = fastBankOfferMapper.map(request);
        return submitApplication(fastBankApplicationRequest)
                .doOnNext(resp -> log.debug("{} submit application response: {}", NAME, resp))
                .flatMap(resp -> findApplication(resp.getId())
                                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(2)))
                                .takeUntil(response -> response.getStatus() == FastBankApplicationResponse.Status.PROCESSED)
                                .doOnNext(response -> log.debug("{} polling response: {}", NAME, response))
                                .last()
                )
                .map(applResp -> fastBankOfferMapper.map(applResp.getOffer()))
                .doOnError(err -> log.error("Error during application processing: {}", err.getMessage(), err));
    }

    private Mono<FastBankApplicationResponse> submitApplication(FastBankApplicationRequest request) {
        log.debug("{} submit application request: {}", NAME, request);
        return fastBankWebClient.post()
                .uri("/applications")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(FastBankApplicationResponse.class);
    }

    private Mono<FastBankApplicationResponse> findApplication(String applicationId) {
        return fastBankWebClient.get()
                .uri("/applications/" + applicationId)
                .retrieve()
                .bodyToMono(FastBankApplicationResponse.class);
    }
}
