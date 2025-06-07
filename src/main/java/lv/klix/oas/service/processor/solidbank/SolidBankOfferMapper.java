package lv.klix.oas.service.processor.solidbank;

import lv.klix.oas.service.processor.OfferDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SolidBankOfferMapper {

    OfferDTO map(SolidBankOfferResponse solidBankOfferResponse);
}