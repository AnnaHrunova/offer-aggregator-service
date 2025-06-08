package lv.klix.oas.service;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OfferDTO {
    private BigDecimal monthlyPaymentAmount;
    private BigDecimal totalRepaymentAmount;
    private BigDecimal numberOfPayments;
    private BigDecimal annualPercentageRate;
    private LocalDate firstRepaymentDate;
}
