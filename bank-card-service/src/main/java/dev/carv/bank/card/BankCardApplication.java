package dev.carv.bank.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan(basePackages = "dev.carv.bank")
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class BankCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankCardApplication.class, args);
    }

}
