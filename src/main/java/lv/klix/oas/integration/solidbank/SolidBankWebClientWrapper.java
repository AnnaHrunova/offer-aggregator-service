package lv.klix.oas.integration.solidbank;

import lv.klix.oas.integration.FinancingInstitutionWebClientWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SolidBankWebClientWrapper extends FinancingInstitutionWebClientWrapper<SolidBankApplicationRequest, SolidBankApplicationResponse> {

    @Autowired
    public SolidBankWebClientWrapper(WebClient solidBankWebClient) {
        super(solidBankWebClient, SolidBankApplicationResponse.class, "FastBank");
    }
}
