package lv.klix.oas.integration.fastbank;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.klix.oas.integration.ApplicationProcessor;
import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@AllArgsConstructor
@Slf4j
public class FastBankApplicationProcessor implements ApplicationProcessor {

    private final FastBankOfferMapper fastBankOfferMapper;
    private final WebClient fastBankWebClient;

    @Override
    public Mono<OfferDTO> process(ApplicationDTO request) {
        var fastBankApplicationRequest = fastBankOfferMapper.map(request);
        return submitApplication(fastBankApplicationRequest)
                .doOnNext(resp -> log.info("Submitted application: {}", resp))
                .flatMap(resp -> findApplication(resp.getId())
                                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(2)))
                                .takeUntil(response -> response.getStatus() == FastBankApplicationResponse.Status.PROCESSED)
                                .doOnNext(response -> log.info("Polling response: {}", response))
                                .last()
                )
                .map(applResp -> fastBankOfferMapper.map(applResp.getOffer()))
                .doOnError(err -> log.error("Error during application processing: {}", err.getMessage(), err));
    }

    private Mono<FastBankApplicationResponse> submitApplication(FastBankApplicationRequest request) {
        return fastBankWebClient.post()
                .uri("/applications")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(FastBankApplicationResponse.class);
    }

    private Mono<FastBankApplicationResponse> findApplication(String applicationId) {
        return fastBankWebClient.get()
                .uri("/applications/" + applicationId)
                .retrieve()
                .bodyToMono(FastBankApplicationResponse.class);
    }
}
