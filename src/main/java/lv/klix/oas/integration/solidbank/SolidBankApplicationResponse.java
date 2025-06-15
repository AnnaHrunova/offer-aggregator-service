package lv.klix.oas.integration.solidbank;

import lombok.Data;
import lv.klix.oas.integration.ApplicationResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SolidBankApplicationResponse implements ApplicationResponse<SolidBankApplicationResponse.SolidBankOfferResponse>
{
    private String id;
    private Status status;
    private SolidBankOfferResponse offer;

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

    @Override
    public boolean isReady() {
        return this.status == Status.PROCESSED;
    }

    @Override
    public SolidBankOfferResponse getOffer() {
        return offer;
    }

    @Override
    public String toString() {
        var response = "SolidBankApplicationResponse {" +
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
