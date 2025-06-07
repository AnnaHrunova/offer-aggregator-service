package lv.klix.oas.service.processor.fastbank;

import lv.klix.oas.service.processor.OfferDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FastBankOfferMapper {

    OfferDTO map(FastBankApplicationResponse.FastBankOfferResponse fastBankOfferResponse);
}