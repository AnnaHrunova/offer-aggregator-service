package lv.klix.oas.service.processor.fastbank;

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
}
