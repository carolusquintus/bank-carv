package dev.carv.bank.account.repository;

import dev.carv.bank.account.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    boolean existsByMobileNumber(String mobileNumber);

    @Query("""
        SELECT c
        FROM CustomerEntity c
        LEFT JOIN FETCH c.account
        WHERE c.mobileNumber = :mobileNumber
    """)
    Optional<CustomerEntity> findByMobileNumberWithAccount(@Param("mobileNumber") String mobileNumber);

}
