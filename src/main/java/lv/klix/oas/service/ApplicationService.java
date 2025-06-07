package lv.klix.oas.service;

import lombok.AllArgsConstructor;
import lv.klix.oas.domain.Application;
import lv.klix.oas.domain.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public UUID createApplication() {
        return applicationRepository.save(new Application()).getId();
    }
}
