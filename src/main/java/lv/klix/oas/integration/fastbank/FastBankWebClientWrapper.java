package lv.klix.oas.integration.fastbank;

import lv.klix.oas.integration.FinancingInstitutionWebClientWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FastBankWebClientWrapper extends FinancingInstitutionWebClientWrapper<FastBankApplicationRequest, FastBankApplicationResponse> {

    @Autowired
    public FastBankWebClientWrapper(WebClient fastBankWebClient) {
        super(fastBankWebClient, FastBankApplicationResponse.class, "FastBank");
    }
}
