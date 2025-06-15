package lv.klix.oas.integration.fastbank;

import lombok.extern.slf4j.Slf4j;
import lv.klix.oas.conf.ProductConfig;
import lv.klix.oas.integration.ApplicationProcessor;
import lv.klix.oas.service.ApplicationDTO;
import lv.klix.oas.validator.PhoneType;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FastBankApplicationProcessor extends
        ApplicationProcessor<FastBankApplicationRequest,
                FastBankApplicationResponse,
                FastBankApplicationResponse.FastBankOfferResponse> {

    public static final String NAME = "FastBank";

    //Lombok is not used because of complex generic types,
    //explicit constructor ensures correct type parameter passing
    public FastBankApplicationProcessor(
            ProductConfig productConfig,
            FastBankWebClientWrapper client,
            FastBankOfferMapper mapper) {
        super(productConfig, client, mapper);
    }

    @Override
    public boolean isApplicable(ApplicationDTO request) {
        return request.getPhone().matches(PhoneType.LATVIA.getRegex());
    }

    @Override
    public String name() {
        return NAME;
    }
}
