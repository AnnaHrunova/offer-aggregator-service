package lv.klix.oas.integration.solidbank;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.klix.oas.integration.ApplicationProcessor;
import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.service.OfferDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@AllArgsConstructor
@Slf4j
public class SolidBankApplicationProcessor implements ApplicationProcessor {
    private final SolidBankClient solidBankClient;
    private final SolidBankOfferMapper solidBankOfferMapper;

    @Override
    public Mono<OfferDTO> process(ApplicationDTO request) {

        var solidBankApplicationRequest = solidBankOfferMapper.map(request);
        return solidBankClient.submitApplication(solidBankApplicationRequest)
                .doOnNext(resp -> log.info("Submitted application: {}", resp))
                .flatMap(resp ->
                        solidBankClient.findApplication(resp.getId())
                                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(2)))
                                .takeUntil(response -> response.getStatus() == SolidBankApplicationResponse.Status.PROCESSED)
                                .doOnNext(response -> log.info("Polling response: {}", response))
                                .last()
                )
                .map(applResp -> solidBankOfferMapper.map(applResp.getOffer()))
                .doOnError(err -> log.error("Error during application processing: {}", err.getMessage(), err));
    }
}

