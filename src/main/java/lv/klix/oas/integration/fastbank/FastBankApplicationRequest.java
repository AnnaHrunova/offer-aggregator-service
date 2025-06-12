package lv.klix.oas.integration.fastbank;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FastBankApplicationRequest {

    private String phoneNumber;
    private String email;
    private BigDecimal monthlyIncomeAmount;
    private BigDecimal monthlyCreditLiabilities;
    private Integer dependents;
    private boolean agreeToDataSharing;
    private BigDecimal amount;

    @Override
    public String toString() {
        return "FastBankApplicationRequest{" +
                "phoneNumber=*****" +
                ", email=*****" +
                ", monthlyIncomeAmount=" + monthlyIncomeAmount +
                ", monthlyCreditLiabilities=" + monthlyCreditLiabilities +
                ", dependents=" + dependents +
                ", agreeToDataSharing=" + agreeToDataSharing +
                ", amount=" + amount +
                '}';
    }
}
