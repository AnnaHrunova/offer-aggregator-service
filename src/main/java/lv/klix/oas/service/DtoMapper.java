package lv.klix.oas.service;

import lv.klix.oas.controller.ApplicationRequest;
import lv.klix.oas.controller.OffersResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applicationId", ignore = true)
    OffersResponse.OfferResponse map(OfferDTO offerDTO);

    ApplicationDTO map(ApplicationRequest request);
}