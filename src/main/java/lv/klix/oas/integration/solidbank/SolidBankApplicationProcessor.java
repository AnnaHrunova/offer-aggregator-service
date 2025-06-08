package lv.klix.oas.integration.solidbank;

import lv.klix.oas.integration.ApplicationProcessor;
import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import reactor.core.publisher.Mono;

public class SolidBankApplicationProcessor implements ApplicationProcessor {

    @Override
    public Mono<OfferDTO> process(ApplicationDTO request) {
        return null;
    }
}
