package lv.klix.oas.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lv.klix.oas.controller.ApplicationRequest;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Application extends BaseEntity{

    @Id
    @UuidGenerator
    private UUID id;

    private String phone;

    private String email;

    private BigDecimal monthlyIncome;

    private BigDecimal monthlyExpenses;

    private BigDecimal monthlyCreditLiabilities;

    private Integer dependents;

    private Boolean isCheckedConsent;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}
