package lv.klix.oas.integration;

import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;

public interface ApplicationMapper<T, R extends ApplicationResponse<O>, O> {

    T mapToRequest(ApplicationDTO applicationDTO);

    OfferDTO mapToOffer(O offer);
}
