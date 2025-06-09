package lv.klix.oas.conf;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebClientConfig {

    private final ProductConfig productConfig;

    @Bean
    public WebClient fastBankWebClient() {
        var fastBankConfig = productConfig.getFinancingInstitutions().get("fastbank");
        log.info("Building FastBank WebClient: {}", fastBankConfig.getUrl());
        return buildWebClient(fastBankConfig.getUrl());
    }

    @Bean
    public WebClient solidBankWebClient() {
        var solidBankConfig = productConfig.getFinancingInstitutions().get("solidbank");
        log.info("Building SolidBank WebClient: {}", solidBankConfig.getUrl());
        return buildWebClient(solidBankConfig.getUrl());
    }

    private WebClient buildWebClient(String url) {
        HttpClient httpClient = HttpClient.create()
                .wiretap(true)
                .responseTimeout(Duration.ofSeconds(1));
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
