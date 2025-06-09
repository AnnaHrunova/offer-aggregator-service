package lv.klix.oas.integration.solidbank;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SolidBankApplicationRequest {
    private String phone;
    private String email;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpenses;
    private MaritalStatus maritalStatus;
    private boolean agreeToBeScored;
    private BigDecimal amount;

    enum MaritalStatus {
        SINGLE,
        MARRIED,
        DIVORCED,
        COHABITING
    }
}
