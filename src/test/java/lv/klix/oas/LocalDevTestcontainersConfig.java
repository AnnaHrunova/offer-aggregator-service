package lv.klix.oas;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class LocalDevTestcontainersConfig {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER() {
        return new PostgreSQLContainer<>(
                DockerImageName.parse("postgres:17.5").asCompatibleSubstituteFor("postgres"))
                .withDatabaseName("offer-aggregator-db")
                .withUsername("postgres")
                .withPassword("password");
    }
}
