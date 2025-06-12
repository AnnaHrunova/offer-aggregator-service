package lv.klix.oas.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lv.klix.oas.service.ApplicationAggregatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${api.v1}/offers")
@AllArgsConstructor
public class OfferController {

    private final ApplicationAggregatorService applicationAggregatorService;

    @PatchMapping
    public Mono<ResponseEntity<OffersResponse.OfferResponse>> selectOffer(@RequestBody @Valid SelectOfferRequest request) {
        return applicationAggregatorService.selectOffer(request)
                .thenReturn(ResponseEntity.ok().build());
    }
}
