package dev.carv.bank.commons.audit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Value("${spring.application.name}")
    private String auditor;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(auditor);
    }

}
