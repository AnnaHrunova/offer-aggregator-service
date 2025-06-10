package lv.klix.oas.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Offer extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private Application application;

    private String financingInstitution;

    private BigDecimal monthlyPaymentAmount;

    private BigDecimal totalRepaymentAmount;

    private Integer numberOfPayments;

    private BigDecimal annualPercentageRate;

    private LocalDate firstRepaymentDate;

    private Boolean isSelected;
}
