package lv.klix.oas.controller;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ApplicationRequest {

    @NotBlank
    @Length(min = 12, max = 16)
    private String phone;

    @NotBlank
    @Email
    @Length(min = 5, max = 100)
    private String email;

    @NotNull
    @Min(0)
    private BigDecimal monthlyIncome;

    @NotNull
    @Min(0)
    private BigDecimal monthlyExpenses;

    @NotNull
    @Min(0)
    private BigDecimal monthlyCreditLiabilities;

    @NotNull
    @Min(0)
    private Integer dependents;

    @NotNull
    private Boolean isCheckedConsent;

    @NotNull
    @Min(100)
    @Max(500000)
    private BigDecimal amount;

    @NotNull
    private MaritalStatus maritalStatus;

    public enum MaritalStatus {
        SINGLE,
        MARRIED,
        DIVORCED,
        COHABITING
    }

}