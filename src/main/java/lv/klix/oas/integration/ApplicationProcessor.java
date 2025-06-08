package lv.klix.oas.integration;

import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import reactor.core.publisher.Mono;

public interface ApplicationProcessor {

    Mono<OfferDTO> process(ApplicationDTO request);
}
