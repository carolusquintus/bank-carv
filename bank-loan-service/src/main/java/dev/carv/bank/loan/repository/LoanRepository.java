package dev.carv.bank.loan.repository;

import dev.carv.bank.loan.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<LoanEntity, UUID> {

    boolean existsByMobileNumber(String mobileNumber);

    Optional<LoanEntity> findByMobileNumber(String mobileNumber);

    Optional<LoanEntity> findByLoanNumber(String loanNumber);

}
