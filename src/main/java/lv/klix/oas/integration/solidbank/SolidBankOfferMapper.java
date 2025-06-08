package lv.klix.oas.integration.solidbank;

import lv.klix.oas.service.OfferDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SolidBankOfferMapper {

    OfferDTO map(SolidBankOfferResponse solidBankOfferResponse);
}