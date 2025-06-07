package lv.klix.oas.service;

import lombok.AllArgsConstructor;
import lv.klix.oas.service.processor.ApplicationProcessor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationAggregatorService {

    private final ApplicationDomainService applicationService;
    private final List<ApplicationProcessor> applicationProcessors;
    private final OfferResponseMapper offerResponseMapper;

    public Flux<OfferResponse> processApplication() {
        applicationService.createApplication();

        return Flux.fromIterable(applicationProcessors)
                .flatMap(processor -> processor.process(null)
                        .onErrorResume(e -> Mono.empty()))
                .map(offerResponseMapper::map);
    }
}