package dev.carv.bank.account.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static dev.carv.bank.account.constant.DBConstants.*;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity {

    @CreatedDate
    @Column(name = CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = UPDATED_AT, insertable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = CREATED_BY, nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = UPDATED_BY, insertable = false)
    private String updatedBy;

}
