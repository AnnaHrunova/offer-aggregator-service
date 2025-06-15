package lv.klix.oas.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
public class FinancingInstitutionWebClientWrapper<T, K> {

    private final WebClient webClient;
    private final Class<K> responseType;
    private final String NAME;

    public FinancingInstitutionWebClientWrapper(WebClient webClient, Class<K> responseType, String name) {
        this.webClient = webClient;
        this.responseType = responseType;
        this.NAME = name;
    }

    public Mono<K> submitApplication(T request, String path) {
        log.debug("{} submit application request: {}", NAME, request);
        return webClient.post()
                .uri(path)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseType);
    }

    public Mono<K> findApplication(String path) {
        return webClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(responseType);
    }
}
