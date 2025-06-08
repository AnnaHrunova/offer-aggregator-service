package lv.klix.oas.service;

import lv.klix.oas.controller.ApplicationRequest;
import lv.klix.oas.controller.OffersResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    OffersResponse.OfferResponse map(OfferDTO offerDTO);

    ApplicationDTO map(ApplicationRequest request);
}