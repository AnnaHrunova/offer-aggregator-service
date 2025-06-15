package lv.klix.oas.integration.fastbank;

import lombok.Data;
import lv.klix.oas.integration.ApplicationResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FastBankApplicationResponse implements ApplicationResponse<FastBankApplicationResponse.FastBankOfferResponse> {

    private String id;

    private Status status;

    private FastBankOfferResponse offer;

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

    @Override
    public FastBankOfferResponse getOffer() {
        return offer;
    }

    @Override
    public boolean isReady() {
        return this.status == Status.PROCESSED;
    }

    @Override
    public String toString() {
        var response = "FastBankApplicationResponse {" +
                "id=" + id +
                ", status=" + status +
                "%s" +
                "}";

        var offerString = offer == null ? "" : ", offer= {" +
                    "monthlyPaymentAmount=" + offer.monthlyPaymentAmount +
                    ", totalRepaymentAmount=" + offer.totalRepaymentAmount +
                    ", numberOfPayments=" + offer.numberOfPayments +
                    ", annualPercentageRate=" + offer.annualPercentageRate +
                    ", firstRepaymentDate=" + offer.firstRepaymentDate + "}";
        return String.format(response, offerString);
    }
}
