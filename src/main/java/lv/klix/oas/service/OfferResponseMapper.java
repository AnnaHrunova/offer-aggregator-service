package lv.klix.oas.service;

import lv.klix.oas.service.processor.OfferDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferResponseMapper {

    OfferResponse map(OfferDTO offerDTO);
}