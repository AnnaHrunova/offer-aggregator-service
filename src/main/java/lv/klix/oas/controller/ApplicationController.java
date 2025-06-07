package lv.klix.oas.controller;

import lombok.AllArgsConstructor;
import lv.klix.oas.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/test")
    public Mono<ResponseEntity<UUID>> createApplication() {
        var uuid = applicationService.createApplication();

        return Mono.just(ResponseEntity.ok(uuid));
    }


}
