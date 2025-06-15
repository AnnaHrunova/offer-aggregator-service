package lv.klix.oas.integration.solidbank;

import lombok.extern.slf4j.Slf4j;
import lv.klix.oas.conf.ProductConfig;
import lv.klix.oas.integration.ApplicationProcessor;
import lv.klix.oas.service.ApplicationDTO;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SolidBankApplicationProcessor extends
        ApplicationProcessor<SolidBankApplicationRequest,
                SolidBankApplicationResponse,
                SolidBankApplicationResponse.SolidBankOfferResponse> {
    public static final String NAME = "SolidBank";

    //Lombok is not used because of complex generic types,
    //Explicit constructor ensures correct type parameter passing
    public SolidBankApplicationProcessor(
            ProductConfig productConfig,
            SolidBankWebClientWrapper client,
            SolidBankOfferMapper mapper) {
        super(productConfig, client, mapper);
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean isApplicable(ApplicationDTO request) {
        return true;
    }

}

