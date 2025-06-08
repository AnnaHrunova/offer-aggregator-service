package lv.klix.oas.service;

import lombok.Data;
import lv.klix.oas.controller.ApplicationRequest;

import java.math.BigDecimal;

@Data
public class ApplicationDTO {

    private String phone;
    private String email;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpenses;
    private BigDecimal monthlyCreditLiabilities;
    private Integer dependents;
    private Boolean isCheckedConsent;
    private BigDecimal amount;
    private ApplicationRequest.MaritalStatus maritalStatus;
}
