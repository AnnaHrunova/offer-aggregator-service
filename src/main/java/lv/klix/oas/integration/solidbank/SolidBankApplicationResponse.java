package lv.klix.oas.integration.solidbank;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SolidBankApplicationResponse {
    private String id;
    private Status status;
    private SolidBankOfferResponse offer;

    @Override
    public String toString() {
        return "SolidBankApplicationResponse{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", offer=" + offer +
                '}';
    }

    @Data
    public static  class SolidBankOfferResponse {

        private BigDecimal monthlyPaymentAmount;
        private BigDecimal totalRepaymentAmount;
        private BigDecimal numberOfPayments;
        private BigDecimal annualPercentageRate;
        private LocalDate firstRepaymentDate;
    }

    enum Status {
        DRAFT,
        PROCESSED
    }
}
