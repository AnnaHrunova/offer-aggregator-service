package lv.klix.oas.service.processor.fastbank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class FastBankClient {

    private final WebClient fastBankWebClient;
    private final ObjectMapper objectMapper;

    public Mono<FastBankApplicationResponse> submitApplication(FastBankApplicationRequest request) {
        try {
            return fastBankWebClient.post()
                    .uri("/applications")
                    .bodyValue(objectMapper.writeValueAsString(request))
                    .retrieve()
                    .bodyToMono(FastBankApplicationResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Mono<FastBankApplicationResponse> findApplication(String applicationId) {
        return fastBankWebClient.get()
                .uri("/applications/" + applicationId)
                .retrieve()
                .bodyToMono(FastBankApplicationResponse.class);
    }
}
