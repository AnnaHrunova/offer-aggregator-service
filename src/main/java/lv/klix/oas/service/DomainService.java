package lv.klix.oas.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.klix.oas.domain.*;
import lv.klix.oas.exception.InvalidDataException;
import lv.klix.oas.exception.InvalidOperationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class DomainService {

    private final ApplicationRepository applicationRepository;
    private final OfferRepository offerRepository;
    private final AesEncryptionService aesEncryptionService;

    @Transactional
    public UUID saveOffer(UUID applicationId, OfferDTO offerData) {
        var application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() -> new InvalidDataException(String.format("Application id=%s not found", applicationId)));
        var offer = new Offer();
        offer.setApplication(application);
        offer.setFinancingInstitution(offerData.getFinancingInstitution());
        offer.setMonthlyPaymentAmount(offerData.getMonthlyPaymentAmount());
        offer.setTotalRepaymentAmount(offerData.getTotalRepaymentAmount());
        offer.setNumberOfPayments(offerData.getNumberOfPayments());
        offer.setAnnualPercentageRate(offerData.getAnnualPercentageRate());
        offer.setFirstRepaymentDate(offerData.getFirstRepaymentDate());
        var savedOffer = offerRepository.save(offer).getId();
        application.setStatus(ApplicationStatus.PROCESSED);
        applicationRepository.save(application);
        return savedOffer;
    }

    public UUID createApplication(ApplicationDTO applicationData) {
        var clientReference = UUID.randomUUID();
        var application = new Application();
        application.setClientReference(clientReference);

        application.setPhone(aesEncryptionService.encrypt(applicationData.getPhone(), clientReference.toString()));
        application.setEmail(aesEncryptionService.encrypt(applicationData.getEmail(), clientReference.toString()));

        application.setStatus(ApplicationStatus.INIT);
        application.setMonthlyIncome(applicationData.getMonthlyIncome());
        application.setMonthlyExpenses(applicationData.getMonthlyExpenses());
        application.setMonthlyCreditLiabilities(applicationData.getMonthlyCreditLiabilities());
        application.setDependents(applicationData.getDependents());
        application.setIsCheckedConsent(applicationData.getIsCheckedConsent());
        application.setAmount(applicationData.getAmount());
        application.setMaritalStatus(MaritalStatus.valueOf(applicationData.getMaritalStatus().name()));
        return applicationRepository.save(application).getId();
    }

    @Transactional
    public void selectOffer(UUID offerId, UUID applicationId) {
        var offer = offerRepository
                .findByIdAndApplicationId(offerId, applicationId)
                .orElseThrow(() -> new InvalidDataException(String.format("Offer id=%s for application id=%s not found", offerId, applicationId)));
        var application = offer.getApplication();
        if (application.getStatus() != ApplicationStatus.PROCESSED) {
            log.warn("Application id={} in status={} is not available for offers selection", applicationId, application.getStatus());
            throw new InvalidOperationException(String.format("Application id=%s in status=%s is not available for offers selection", applicationId, application.getStatus()));
        }
        offer.setIsSelected(true);
        offerRepository.save(offer);

        offerRepository.findByApplicationIdAndIdNot(applicationId, offerId)
                        .forEach(o -> {
                            o.setIsSelected(false);
                            offerRepository.save(o);
                        });

        application.setStatus(ApplicationStatus.FINALIZED);
        applicationRepository.save(application);
    }
}
