package lv.klix.oas.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "product-config")
@Data
public class ProductConfig {
    private Map<String, FinancingInstitutionProperties> financingInstitutions;

    @Data
    public static class FinancingInstitutionProperties {
        private boolean enabled;
        private String url;
    }
}
