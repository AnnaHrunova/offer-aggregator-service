package lv.klix.oas.integration.fastbank;

import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FastBankOfferMapper {

    OfferDTO map(FastBankApplicationResponse.FastBankOfferResponse fastBankOfferResponse);

    @Mapping(source = "phone", target = "phoneNumber")
    @Mapping(source = "monthlyIncome", target = "monthlyIncomeAmount")
    @Mapping(source = "isCheckedConsent", target = "agreeToDataSharing")
    FastBankApplicationRequest map(ApplicationDTO applicationDTO);
}
