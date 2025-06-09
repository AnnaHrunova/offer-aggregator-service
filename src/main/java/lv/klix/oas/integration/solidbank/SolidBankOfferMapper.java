package lv.klix.oas.integration.solidbank;

import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SolidBankOfferMapper {

    @Mapping(target = "financingInstitution", constant = "SolidBank")
    OfferDTO map(SolidBankApplicationResponse.SolidBankOfferResponse solidBankOfferResponse);

    @Mapping(source = "isCheckedConsent", target = "agreeToBeScored")
    SolidBankApplicationRequest map(ApplicationDTO applicationDTO);
}