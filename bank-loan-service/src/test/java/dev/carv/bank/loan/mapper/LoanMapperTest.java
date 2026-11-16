package dev.carv.bank.loan.mapper;

import dev.carv.bank.loan.constant.LoanType;
import dev.carv.bank.loan.dto.LoanDto;
import dev.carv.bank.loan.entity.LoanEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LoanMapperTest {

    private final LoanMapper mapper = Mappers.getMapper(LoanMapper.class);

    @Test
    @DisplayName("toDto should map entity to DTO correctly")
    void toDto_Success() {
        LoanEntity entity = new LoanEntity();
        entity.setId(UUID.randomUUID());
        entity.setMobileNumber("525512345678");
        entity.setLoanNumber("75255123456758");
        entity.setType(LoanType.CAR);
        entity.setTotalLoan(new BigDecimal("50000.00"));
        entity.setPaidLoan(new BigDecimal("5000.00"));
        entity.setOutstandingLoan(new BigDecimal("45000.00"));

        LoanDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.mobileNumber()).isEqualTo(entity.getMobileNumber());
        assertThat(dto.loanNumber()).isEqualTo(entity.getLoanNumber());
        assertThat(dto.type()).isEqualTo(LoanType.CAR.name());
        assertThat(dto.totalLoan()).isEqualTo(entity.getTotalLoan());
        assertThat(dto.paidLoan()).isEqualTo(entity.getPaidLoan());
        assertThat(dto.outstandingLoan()).isEqualTo(entity.getOutstandingLoan());
    }

    @Test
    @DisplayName("toDto should return null when entity is null")
    void toDto_Null() {
        assertThat(mapper.toDto(null)).isNull();
    }

    @Test
    @DisplayName("toEntity should map DTO to entity correctly")
    void toEntity_Success() {
        LoanDto dto = new LoanDto(
            "525512345678",
            "75255123456758",
            "STUDENT",
            new BigDecimal("25000.00"),
            new BigDecimal("2500.00"),
            new BigDecimal("22500.00")
        );

        LoanEntity entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getMobileNumber()).isEqualTo(dto.mobileNumber());
        assertThat(entity.getLoanNumber()).isEqualTo(dto.loanNumber());
        assertThat(entity.getType()).isEqualTo(LoanType.STUDENT);
        assertThat(entity.getTotalLoan()).isEqualTo(dto.totalLoan());
        assertThat(entity.getPaidLoan()).isEqualTo(dto.paidLoan());
        assertThat(entity.getOutstandingLoan()).isEqualTo(dto.outstandingLoan());
    }

    @Test
    @DisplayName("toEntity should return null when DTO is null")
    void toEntity_Null() {
        assertThat(mapper.toEntity(null)).isNull();
    }
}
