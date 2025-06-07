package lv.klix.oas.service.processor.solidbank;

import lv.klix.oas.service.processor.ApplicationDTO;
import lv.klix.oas.service.processor.ApplicationProcessor;
import lv.klix.oas.service.processor.OfferDTO;
import reactor.core.publisher.Mono;

public class SolidBankApplicationProcessor implements ApplicationProcessor {

    @Override
    public Mono<OfferDTO> process(ApplicationDTO request) {
        return null;
    }
}
