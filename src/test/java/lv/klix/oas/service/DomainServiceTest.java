package lv.klix.oas.service;

import lv.klix.oas.LocalDevTestcontainersConfig;
import lv.klix.oas.domain.*;
import lv.klix.oas.exception.InvalidDataException;
import lv.klix.oas.exception.InvalidOperationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(LocalDevTestcontainersConfig.class)
public class DomainServiceTest {

    @Autowired
    private DomainService domainService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private OfferRepository offerRepository;


    @Test
    void shouldSelectOfferAndUpdateApplicationStatus() {
        var application = prepareValidApplication(ApplicationStatus.PROCESSED);
        var applicationId = applicationRepository.save(application).getId();

        var offer = prepareOffer(application);
        var offerId = offerRepository.save(offer).getId();

        domainService.selectOffer(offerId, applicationId);

        var updatedOffer = offerRepository.findById(offerId).orElseThrow();
        var updatedApp = applicationRepository.findById(applicationId).orElseThrow();

        assertTrue(updatedOffer.getIsSelected());
        assertEquals(ApplicationStatus.FINALIZED, updatedApp.getStatus());
    }

    @Test
    void shouldThrowIfOfferNotFound() {
        var application = prepareValidApplication(ApplicationStatus.PROCESSED);
        var applicationId = applicationRepository.save(application).getId();

        var fakeOfferId = UUID.randomUUID();
        var ex = assertThrows(
                InvalidDataException.class,
                () -> domainService.selectOffer(fakeOfferId, applicationId)
        );

        assertEquals(String.format("Offer id=%s for application id=%s not found", fakeOfferId, applicationId), ex.getMessage());
    }

    @Test
    void shouldThrowIfApplicationStatusNotProcessed() {
        var finalizedApplication = prepareValidApplication(ApplicationStatus.FINALIZED);
        var finalizedApplicationId = applicationRepository.save(finalizedApplication).getId();

        var offer = prepareOffer(finalizedApplication);
        var offerId = offerRepository.save(offer).getId();

        InvalidOperationException ex = assertThrows(
                InvalidOperationException.class,
                () -> domainService.selectOffer(offerId, finalizedApplication.getId())
        );

        assertEquals(String.format("Application id=%s in status=%s is not available for offers selection", finalizedApplicationId, ApplicationStatus.FINALIZED), ex.getMessage());
    }

    private static Application prepareValidApplication(ApplicationStatus status) {
        var application = new Application();
        application.setStatus(status);
        application.setPhone("+37123456789");
        application.setEmail("email999@email.com");
        application.setMonthlyIncome(new BigDecimal("50000.00"));
        application.setMonthlyExpenses(new BigDecimal("1000.00"));
        application.setMonthlyCreditLiabilities(BigDecimal.ZERO);
        application.setDependents(0);
        application.setIsCheckedConsent(true);
        application.setAmount(new BigDecimal("1000.00"));
        application.setMaritalStatus(MaritalStatus.SINGLE);
        return application;
    }

    private static Offer prepareOffer(Application application) {
        var offer = new Offer();
        offer.setApplication(application);
        offer.setFinancingInstitution("FastBank");
        offer.setMonthlyPaymentAmount(new BigDecimal("500.00"));
        offer.setTotalRepaymentAmount(new BigDecimal("5000000.00"));
        offer.setNumberOfPayments(10);
        offer.setAnnualPercentageRate(new BigDecimal("0.001"));
        offer.setFirstRepaymentDate(LocalDate.now());
        offer.setIsSelected(false);
        return offer;
    }
}
