package lv.klix.oas.service;

import lombok.AllArgsConstructor;
import lv.klix.oas.controller.ApplicationRequest;
import lv.klix.oas.controller.OffersResponse;
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

    public Flux<OffersResponse.OfferResponse> processApplication(ApplicationRequest request) {
        var application = dtoMapper.map(request);
        return Mono.fromCallable(() -> domainService.createApplication(application))
            .subscribeOn(Schedulers.boundedElastic())
            .flatMapMany(applicationId ->
                Flux.fromIterable(applicationProcessors)
                    .flatMap(processor -> processor.process(application)
                        .onErrorResume(e -> Mono.empty()))
                    .flatMap(o ->
                            Mono.fromCallable(() -> storeApplicationOffer(applicationId, o))
                            .subscribeOn(Schedulers.boundedElastic()))
            );
    }

    private OffersResponse.OfferResponse storeApplicationOffer(UUID applicationId, OfferDTO offerDTO) {
        var savedOfferId = domainService.saveOffer(applicationId, offerDTO);
        var res = dtoMapper.map(offerDTO);
        res.setId(savedOfferId);
        return res;
    }
}