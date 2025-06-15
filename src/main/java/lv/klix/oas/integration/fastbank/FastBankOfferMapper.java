package lv.klix.oas.integration.fastbank;

import lv.klix.oas.integration.ApplicationMapper;
import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FastBankOfferMapper extends
        ApplicationMapper<FastBankApplicationRequest,
                FastBankApplicationResponse,
                FastBankApplicationResponse.FastBankOfferResponse> {

    @Override
    @Mapping(source = "phone", target = "phoneNumber")
    @Mapping(source = "monthlyIncome", target = "monthlyIncomeAmount")
    @Mapping(source = "isCheckedConsent", target = "agreeToDataSharing")
    FastBankApplicationRequest mapToRequest(ApplicationDTO applicationDTO);

    @Override
    @Mapping(target = "financingInstitution", constant = FastBankApplicationProcessor.NAME)
    OfferDTO mapToOffer(FastBankApplicationResponse.FastBankOfferResponse response);
}
