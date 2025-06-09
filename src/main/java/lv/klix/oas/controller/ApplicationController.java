package lv.klix.oas.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lv.klix.oas.service.ApplicationAggregatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.v1}/applications")
@AllArgsConstructor
public class ApplicationController {

    private final ApplicationAggregatorService applicationAggregatorService;

    @PostMapping
    public Mono<ResponseEntity<OffersResponse>> submitApplication(@RequestBody @Valid ApplicationRequest request) {
        return applicationAggregatorService.processApplication(request)
                .collect(Collectors.toSet())
                .map(offers -> ResponseEntity.ok(new OffersResponse(offers)));
    }
}
