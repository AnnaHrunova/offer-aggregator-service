package lv.klix.oas.service.processor;

import reactor.core.publisher.Mono;

public interface ApplicationProcessor {

    Mono<OfferDTO> process(ApplicationDTO request);
}
