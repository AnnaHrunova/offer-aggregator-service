package lv.klix.oas.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lv.klix.oas.service.ApplicationAggregatorService;
import lv.klix.oas.service.OfferResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
@RequestMapping("/applications")
@AllArgsConstructor
public class ApplicationController {

    private final ApplicationAggregatorService applicationAggregatorService;

    @PostMapping
    public Mono<ResponseEntity<OffersResponse>> createApplication() {
        var offers = applicationAggregatorService.processApplication();
        return Mono.just(ResponseEntity.ok(new OffersResponse(offers)));
    }

    @Data
    @AllArgsConstructor
    public static class OffersResponse {
        private Set<OfferResponse> offers;
    }
}
