package lv.klix.oas.integration.fastbank;

import lv.klix.oas.service.OfferDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FastBankOfferMapper {

    OfferDTO map(FastBankApplicationResponse.FastBankOfferResponse fastBankOfferResponse);
}