package lv.klix.oas.integration.fastbank;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.klix.oas.integration.ApplicationProcessor;
import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@AllArgsConstructor
@Slf4j
public class FastBankApplicationProcessor implements ApplicationProcessor {

    private final FastBankClient fastBankClient;
    private final FastBankOfferMapper fastBankOfferMapper;

    @Override
    public Mono<OfferDTO> process(ApplicationDTO request) {
        var fastBankApplicationRequest = fastBankOfferMapper.map(request);
        return fastBankClient.submitApplication(fastBankApplicationRequest)
                .doOnNext(resp -> log.info("Submitted application: {}", resp))
                .flatMap(resp ->
                        fastBankClient.findApplication(resp.getId())
                                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(2)))
                                .takeUntil(response -> response.getStatus() == FastBankApplicationResponse.Status.PROCESSED)
                                .doOnNext(response -> log.info("Polling response: {}", response))
                                .last()
                )
                .map(applResp -> fastBankOfferMapper.map(applResp.getOffer()))
                .doOnError(err -> log.error("Error during application processing: {}", err.getMessage(), err));    }
}
