package lv.klix.oas.integration.solidbank;

import lv.klix.oas.integration.ApplicationMapper;
import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SolidBankOfferMapper extends
        ApplicationMapper<SolidBankApplicationRequest,
                SolidBankApplicationResponse,
                SolidBankApplicationResponse.SolidBankOfferResponse> {

    @Mapping(target = "financingInstitution", constant = "SolidBank")
    OfferDTO mapToOffer(SolidBankApplicationResponse.SolidBankOfferResponse offer);

    @Mapping(source = "isCheckedConsent", target = "agreeToBeScored")
    SolidBankApplicationRequest mapToRequest(ApplicationDTO applicationDTO);
}