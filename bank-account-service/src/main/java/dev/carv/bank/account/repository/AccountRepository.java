package dev.carv.bank.account.repository;

import dev.carv.bank.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    @Query("""
        SELECT a
        FROM AccountEntity a
        JOIN FETCH a.customer
        WHERE a.accountNumber = :accountNumber
    """)
    Optional<AccountEntity> findByAccountNumber(@Param("accountNumber") Long accountNumber);

}
