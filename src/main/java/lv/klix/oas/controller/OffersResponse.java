package lv.klix.oas.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OffersResponse {
    private Set<OfferResponse> offers;

    @Data
    public static class OfferResponse {
        private UUID id;
        private UUID applicationId;
        private String financingInstitution;
        private BigDecimal monthlyPaymentAmount;
        private BigDecimal totalRepaymentAmount;
        private Integer numberOfPayments;
        private BigDecimal annualPercentageRate;
        private LocalDate firstRepaymentDate;
    }
}