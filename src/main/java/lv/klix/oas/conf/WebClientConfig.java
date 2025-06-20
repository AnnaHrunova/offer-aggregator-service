package lv.klix.oas.conf;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.klix.oas.integration.fastbank.FastBankApplicationProcessor;
import lv.klix.oas.integration.solidbank.SolidBankApplicationProcessor;
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
        var fastBankConfig = productConfig.getFinancingInstitutions().get(FastBankApplicationProcessor.NAME);
        log.info("Building FastBank WebClient: {}", fastBankConfig.getUrl());
        return buildWebClient(fastBankConfig.getUrl());
    }

    @Bean
    public WebClient solidBankWebClient() {
        var solidBankConfig = productConfig.getFinancingInstitutions().get(SolidBankApplicationProcessor.NAME);
        log.info("Building SolidBank WebClient: {}", solidBankConfig.getUrl());
        return buildWebClient(solidBankConfig.getUrl());
    }

    private WebClient buildWebClient(String url) {
        HttpClient httpClient = HttpClient.create()
                //.wiretap(true) //print WebClient request and response if DEBUG level enabled for web. Not supposed to be used on PRODUCTION
                .responseTimeout(Duration.ofSeconds(1));
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
