package lv.klix.oas.service;

import lombok.AllArgsConstructor;
import lv.klix.oas.domain.Application;
import lv.klix.oas.domain.ApplicationRepository;
import lv.klix.oas.domain.Offer;
import lv.klix.oas.domain.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DomainService {

    private final ApplicationRepository applicationRepository;
    private final OfferRepository offerRepository;

    public UUID saveOffer(UUID applicationId, OfferDTO offerData) {
        var application = applicationRepository.findById(applicationId).orElseThrow();
        var offer = new Offer();
        offer.setApplication(application);
        return offerRepository.save(offer).getId();
    }

    public UUID createApplication(ApplicationDTO applicationData) {
        var application = new Application();
        return applicationRepository.save(application).getId();
    }
}
