package dev.carv.bank.account.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class AuditEntity {

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "CREATED_BY", insertable = false)
    private String createdBy;

    @Column(name = "UPDATED_BY", insertable = false)
    private String updatedBy;

}
