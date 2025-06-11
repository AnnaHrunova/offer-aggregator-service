package lv.klix.oas.integration.fastbank;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Slf4j
public class FastBankApplicationProcessor extends ApplicationProcessor {
    public static final String NAME = "FastBank";

    private final FastBankOfferMapper fastBankOfferMapper;
    private final WebClient fastBankWebClient;
    private final ProductConfig productConfig;

    @Override
    public boolean isApplicable(ApplicationDTO request) {
        return request.getPhone().matches(PhoneType.LATVIA.getRegex());
    }

    @Override
    public boolean isEnabled() {
        return productConfig
                .getFinancingInstitutions()
                .get(NAME)
                .isEnabled();
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Mono<OfferDTO> process(ApplicationDTO request) {
        var fastBankApplicationRequest = fastBankOfferMapper.map(request);
        return submitApplication(fastBankApplicationRequest)
                .doOnNext(resp -> log.info("Submitted application: {}", resp))
                .flatMap(resp -> findApplication(resp.getId())
                                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(2)))
                                .takeUntil(response -> response.getStatus() == FastBankApplicationResponse.Status.PROCESSED)
                                .doOnNext(response -> log.info("Polling response: {}", response))
                                .last()
                )
                .map(applResp -> fastBankOfferMapper.map(applResp.getOffer()))
                .doOnError(err -> log.error("Error during application processing: {}", err.getMessage(), err));
    }

    private Mono<FastBankApplicationResponse> submitApplication(FastBankApplicationRequest request) {
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
