package lv.klix.oas.integration.solidbank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SolidBankClient {

    private final WebClient solidBankWebClient;
    private final ObjectMapper objectMapper;

    public Mono<SolidBankApplicationResponse> submitApplication(SolidBankApplicationRequest request) {
        try {
            return solidBankWebClient.post()
                    .uri("/applications")
                    .bodyValue(objectMapper.writeValueAsString(request))
                    .retrieve()
                    .bodyToMono(SolidBankApplicationResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Mono<SolidBankApplicationResponse> findApplication(String applicationId) {
        return solidBankWebClient.get()
                .uri("/applications/" + applicationId)
                .retrieve()
                .bodyToMono(SolidBankApplicationResponse.class);
    }
}
