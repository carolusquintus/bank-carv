package dev.carv.bank.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static dev.carv.bank.account.constant.DBConstants.*;

@Data
@MappedSuperclass
public class AuditEntity {

    @CreatedDate
    @Column(name = CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = UPDATED_AT, insertable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = CREATED_BY, nullable = false, updatable = false)
    private String createdBy = "bank-account-service";

    @LastModifiedBy
    @Value("${spring.application.name}")
    @Column(name = UPDATED_BY, insertable = false)
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }

}
