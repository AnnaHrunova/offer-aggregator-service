package lv.klix.oas.service;

import lombok.AllArgsConstructor;
import lv.klix.oas.service.processor.ApplicationProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApplicationAggregatorService {

    private final ApplicationDomainService applicationService;
    private final List<ApplicationProcessor> applicationProcessors;
    private final OfferResponseMapper offerResponseMapper;

    public Set<OfferResponse> processApplication() {
        applicationService.createApplication();
        return applicationProcessors.stream()
                .map(processor -> processor.process(null))
                .map(offerResponseMapper::map)
                .collect(Collectors.toSet());
    }
}
