package lv.klix.oas.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lv.klix.oas.controller.ApplicationRequest;
import lv.klix.oas.controller.OffersResponse;
import lv.klix.oas.controller.SelectOfferRequest;
import lv.klix.oas.domain.ApplicationStatus;
import lv.klix.oas.integration.ApplicationProcessor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ApplicationAggregatorService {

    private final DomainService domainService;
    private final List<ApplicationProcessor> applicationProcessors;
    private final DtoMapper dtoMapper;

    @PostConstruct
    void setup() {
        applicationProcessors
                .removeIf(processor -> !processor.isEnabled());
    }

    public Flux<OffersResponse.OfferResponse> processApplication(ApplicationRequest request) {
        var application = dtoMapper.map(request);
        return Mono.fromCallable(() -> domainService.createApplication(application))
            .subscribeOn(Schedulers.boundedElastic())
            .flatMapMany(applicationId ->
                Flux.fromIterable(applicationProcessors)
                    .flatMap(processor -> processor.apply(application)
                        .onErrorResume(e -> Mono.empty()))
                    .flatMap(offer ->
                            Mono.fromCallable(() -> storeApplicationOffer(applicationId, offer))
                            .subscribeOn(Schedulers.boundedElastic()))
            );
    }

    public Mono<Void> selectOffer(SelectOfferRequest request) {
        return Mono.fromRunnable(() ->
                    domainService.selectOffer(request.getId(), request.getApplicationId()))
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }

    private OffersResponse.OfferResponse storeApplicationOffer(UUID applicationId, OfferDTO offerDTO) {
        var savedOfferId = domainService.saveOffer(applicationId, offerDTO);
        var res = dtoMapper.map(offerDTO);
        res.setId(savedOfferId);
        res.setApplicationId(applicationId);
        return res;
    }
}