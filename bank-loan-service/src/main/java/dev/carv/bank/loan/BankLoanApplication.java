package dev.carv.bank.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan(basePackages = "dev.carv.bank")
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class BankLoanApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankLoanApplication.class, args);
    }

}
