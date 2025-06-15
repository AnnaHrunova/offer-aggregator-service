package lv.klix.oas.integration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.klix.oas.conf.ProductConfig;
import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import reactor.core.publisher.Mono;

import java.time.Duration;

@AllArgsConstructor
@Slf4j
public abstract class ApplicationProcessor <T, R extends ApplicationResponse<O>, O> {

    private final ProductConfig productConfig;
    private final FinancingInstitutionWebClientWrapper<T, R> financingInstitutionWebClient;
    private final ApplicationMapper<T, R, O> mapper;

    public boolean isEnabled() {
        var isEnabled = productConfig
                .getFinancingInstitutions()
                .get(name())
                .isEnabled();
        log.info("{} application processor enabled={}", name(), isEnabled);
        return isEnabled;
    }

    /**
     * Process application by financing institution only if application data is suitable.
     * For example, SolidBank can process applications with international phone numbers, but
     * FastBank Latvian phone numbers only.
    **/
    public Mono<OfferDTO> apply(ApplicationDTO request) {
        if (!isApplicable(request)) {
            log.info("Financing institution {} is not suitable for processing application for applicant's email = {}", name(), request.getEmail());
            return Mono.empty();
        }

        log.info("Financing institution {} started processing application for applicant's email = {}", name(), request.getEmail());
        return process(request);
    }

    public Mono<OfferDTO> process(ApplicationDTO request) {
        T applicationRequest = mapper.mapToRequest(request);
        return financingInstitutionWebClient.submitApplication(applicationRequest, "/applications")
                .doOnNext(resp -> log.debug("{} submit application response: {}", name(), resp))
                .flatMap(resp -> financingInstitutionWebClient.findApplication("/applications/" + resp.getId())
                        .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(2)))
                        .takeUntil(ApplicationResponse::isReady)
                        .doOnNext(response -> log.debug("{} polling response: {}", name(), response))
                        .last()
                )
                .map(r -> mapper.mapToOffer(r.getOffer()))
                .doOnError(err -> log.error("Error during application processing: {}", err.getMessage(), err));
    }


    public abstract boolean isApplicable(ApplicationDTO request);
    public abstract String name();
}
