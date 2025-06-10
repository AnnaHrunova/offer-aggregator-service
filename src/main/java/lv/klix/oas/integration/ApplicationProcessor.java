package lv.klix.oas.integration;

import lombok.extern.slf4j.Slf4j;
import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class ApplicationProcessor {

    public Mono<OfferDTO> apply(ApplicationDTO request) {
        if (!isEnabled()) {
            log.info("Processor {} is disabled", name());
            return Mono.empty();
        }

        if (!isApplicable(request)) {
            log.info("Financing institution {} is not suitable for processing application for applicant's email = {}", name(), request.getEmail());
            return Mono.empty();
        }

        log.info("Financing institution {} started processing application for applicant's email = {}", name(), request.getEmail());
        return process(request);
    }

    public abstract Mono<OfferDTO> process(ApplicationDTO request);
    public abstract boolean isApplicable(ApplicationDTO request);
    public abstract boolean isEnabled();
    public abstract String name();
}
