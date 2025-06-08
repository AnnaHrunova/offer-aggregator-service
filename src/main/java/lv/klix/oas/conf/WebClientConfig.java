package lv.klix.oas.conf;

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
@Slf4j
public class WebClientConfig {

    @Bean
    public WebClient fastBankWebClient() {
        HttpClient httpClient = HttpClient.create()
                .wiretap(true)
                .responseTimeout(Duration.ofSeconds(1));
        return WebClient.builder()
                .baseUrl("https://shop.stage.klix.app/api/FastBank")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public WebClient solidBankWebClient() {
        return WebClient.builder()
                .baseUrl("https://shop.stage.klix.app/api/SolidBank")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
