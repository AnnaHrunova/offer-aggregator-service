package lv.klix.oas.controller;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lv.klix.oas.service.validator.Phone;
import lv.klix.oas.service.validator.PhoneType;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ApplicationRequest {

    @NotBlank
    @Phone({PhoneType.LATVIA, PhoneType.INTERNATIONAL})
    private String phone;

    @NotBlank
    @Email
    @Length(min = 5, max = 100)
    private String email;

    @NotNull
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "1000000.00")
    @Digits(integer = 7, fraction = 2)
    private BigDecimal monthlyIncome;

    @NotNull
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "1000000.00")
    @Digits(integer = 7, fraction = 2)
    private BigDecimal monthlyExpenses;

    @NotNull
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "1000000.00")
    @Digits(integer = 7, fraction = 2)
    private BigDecimal monthlyCreditLiabilities;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer dependents;

    @AssertTrue(message = "You must accept the consent to proceed.")
    private Boolean isCheckedConsent;

    @NotNull
    @DecimalMin(value = "100.00")
    @DecimalMax(value = "1000000.00")
    @Digits(integer = 7, fraction = 2)
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