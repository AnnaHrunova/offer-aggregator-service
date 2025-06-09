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
        offer.setFinancingInstitution(offerData.getFinancingInstitution());
        offer.setMonthlyPaymentAmount(offerData.getMonthlyPaymentAmount());
        offer.setTotalRepaymentAmount(offerData.getTotalRepaymentAmount());
        offer.setNumberOfPayments(offerData.getNumberOfPayments());
        offer.setAnnualPercentageRate(offerData.getAnnualPercentageRate());
        offer.setFirstRepaymentDate(offerData.getFirstRepaymentDate());
        return offerRepository.save(offer).getId();
    }

    public UUID createApplication(ApplicationDTO applicationData) {
        var application = new Application();
        application.setPhone(applicationData.getPhone());
        application.setEmail(applicationData.getEmail());
        application.setMonthlyIncome(applicationData.getMonthlyIncome());
        application.setMonthlyExpenses(applicationData.getMonthlyExpenses());
        application.setMonthlyCreditLiabilities(applicationData.getMonthlyCreditLiabilities());
        application.setDependents(applicationData.getDependents());
        application.setIsCheckedConsent(applicationData.getIsCheckedConsent());
        application.setAmount(applicationData.getAmount());
        application.setMaritalStatus(applicationData.getMaritalStatus());
        return applicationRepository.save(application).getId();
    }
}
