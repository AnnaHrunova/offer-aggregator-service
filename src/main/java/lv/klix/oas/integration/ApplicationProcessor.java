package lv.klix.oas.integration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.klix.oas.conf.ProductConfig;
import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
public abstract class ApplicationProcessor {

    private final ProductConfig productConfig;

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

    public abstract Mono<OfferDTO> process(ApplicationDTO request);
    public abstract boolean isApplicable(ApplicationDTO request);
    public abstract String name();
}
