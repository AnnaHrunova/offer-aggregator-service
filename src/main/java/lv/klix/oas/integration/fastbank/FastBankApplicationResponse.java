package lv.klix.oas.integration.fastbank;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FastBankApplicationResponse {

    private String id;
    private Status status;
    private FastBankOfferResponse offer;

    @Override
    public String toString() {
        return "FastBankApplicationResponse{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", offer=" + offer +
                '}';
    }

    @Data
    public static  class FastBankOfferResponse {

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
