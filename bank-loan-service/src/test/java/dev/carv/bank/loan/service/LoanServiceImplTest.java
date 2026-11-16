package dev.carv.bank.loan.service;

import dev.carv.bank.commons.exception.ResourceAlreadyExistsException;
import dev.carv.bank.commons.exception.ResourceNotFoundException;
import dev.carv.bank.loan.constant.LoanType;
import dev.carv.bank.loan.dto.LoanDto;
import dev.carv.bank.loan.entity.LoanEntity;
import dev.carv.bank.loan.mapper.LoanMapper;
import dev.carv.bank.loan.repository.LoanRepository;
import dev.carv.bank.loan.service.impl.LoanServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @Mock
    private LoanMapper mapper;

    @Mock
    private LoanRepository repository;

    @InjectMocks
    private LoanServiceImpl service;

    private static final String MOBILE_NUMBER = "525512345678";
    private static final String LOAN_NUMBER = "75255123456758";

    @Test
    @DisplayName("createLoan should save a new loan when mobile number does not exist")
    void createLoan_Success() {
        when(repository.existsByMobileNumber(MOBILE_NUMBER)).thenReturn(false);

        service.createLoan(MOBILE_NUMBER);

        ArgumentCaptor<LoanEntity> captor = ArgumentCaptor.forClass(LoanEntity.class);
        verify(repository).save(captor.capture());

        LoanEntity savedLoan = captor.getValue();
        assertThat(savedLoan).isNotNull();
        assertThat(savedLoan.getMobileNumber()).isEqualTo(MOBILE_NUMBER);
        assertThat(savedLoan.getLoanNumber()).isNotNull();
        assertThat(savedLoan.getType()).isNotNull();
        assertThat(savedLoan.getTotalLoan()).isPositive();
        assertThat(savedLoan.getPaidLoan()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(savedLoan.getOutstandingLoan()).isEqualTo(savedLoan.getTotalLoan());
    }

    @Test
    @DisplayName("createLoan should throw ResourceAlreadyExistsException when mobile number already exists")
    void createLoan_AlreadyExists() {
        when(repository.existsByMobileNumber(MOBILE_NUMBER)).thenReturn(true);

        assertThatThrownBy(() -> service.createLoan(MOBILE_NUMBER))
            .isInstanceOf(ResourceAlreadyExistsException.class)
            .hasMessageContaining("Loan")
            .hasMessageContaining("mobileNumber")
            .hasMessageContaining(MOBILE_NUMBER);

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("fetchLoan should return LoanDto when loan is found")
    void fetchLoan_Success() {
        LoanEntity entity = createLoanEntity();
        LoanDto expectedDto = createLoanDto();

        when(repository.findByMobileNumber(MOBILE_NUMBER)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(expectedDto);

        LoanDto actualDto = service.fetchLoan(MOBILE_NUMBER);

        assertThat(actualDto).isEqualTo(expectedDto);
        verify(repository).findByMobileNumber(MOBILE_NUMBER);
        verify(mapper).toDto(entity);
    }

    @Test
    @DisplayName("fetchLoan should throw ResourceNotFoundException when loan does not exist")
    void fetchLoan_NotFound() {
        when(repository.findByMobileNumber(MOBILE_NUMBER)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.fetchLoan(MOBILE_NUMBER))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Loan")
            .hasMessageContaining("mobileNumber")
            .hasMessageContaining(MOBILE_NUMBER);

        verify(repository).findByMobileNumber(MOBILE_NUMBER);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("updateLoan should update entity fields and return true when loan exists")
    void updateLoan_Success() {
        LoanDto dto = createLoanDto();
        LoanEntity entity = createLoanEntity();

        when(repository.findByLoanNumber(dto.loanNumber())).thenReturn(Optional.of(entity));

        boolean updated = service.updateLoan(dto);

        assertThat(updated).isTrue();
        assertThat(entity.getMobileNumber()).isEqualTo(dto.mobileNumber());
        assertThat(entity.getLoanNumber()).isEqualTo(dto.loanNumber());
        assertThat(entity.getType()).isEqualTo(LoanType.valueOf(dto.type()));
        assertThat(entity.getTotalLoan()).isEqualTo(dto.totalLoan());
        assertThat(entity.getPaidLoan()).isEqualTo(dto.paidLoan());
        assertThat(entity.getOutstandingLoan()).isEqualTo(dto.outstandingLoan());
        verify(repository).findByLoanNumber(dto.loanNumber());
    }

    @Test
    @DisplayName("updateLoan should throw ResourceNotFoundException when loan does not exist")
    void updateLoan_NotFound() {
        LoanDto dto = createLoanDto();

        when(repository.findByLoanNumber(dto.loanNumber())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateLoan(dto))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Loan")
            .hasMessageContaining("loanNumber")
            .hasMessageContaining(dto.loanNumber());

        verify(repository).findByLoanNumber(dto.loanNumber());
    }

    @Test
    @DisplayName("deleteLoan should delete loan by id and return true when loan exists")
    void deleteLoan_Success() {
        LoanEntity entity = createLoanEntity();
        when(repository.findByMobileNumber(MOBILE_NUMBER)).thenReturn(Optional.of(entity));

        boolean deleted = service.deleteLoan(MOBILE_NUMBER);

        assertThat(deleted).isTrue();
        verify(repository).findByMobileNumber(MOBILE_NUMBER);
        verify(repository).deleteById(entity.getId());
    }

    @Test
    @DisplayName("deleteLoan should throw ResourceNotFoundException when loan does not exist")
    void deleteLoan_NotFound() {
        when(repository.findByMobileNumber(MOBILE_NUMBER)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteLoan(MOBILE_NUMBER))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Loan")
            .hasMessageContaining("mobileNumber")
            .hasMessageContaining(MOBILE_NUMBER);

        verify(repository).findByMobileNumber(MOBILE_NUMBER);
        verify(repository, never()).deleteById(any());
    }

    private LoanEntity createLoanEntity() {
        LoanEntity entity = new LoanEntity();
        entity.setId(UUID.randomUUID());
        entity.setMobileNumber(MOBILE_NUMBER);
        entity.setLoanNumber(LOAN_NUMBER);
        entity.setType(LoanType.HOME);
        entity.setTotalLoan(new BigDecimal("100000.00"));
        entity.setPaidLoan(new BigDecimal("10000.00"));
        entity.setOutstandingLoan(new BigDecimal("90000.00"));
        return entity;
    }

    private LoanDto createLoanDto() {
        return new LoanDto(
            MOBILE_NUMBER,
            LOAN_NUMBER,
            LoanType.HOME.name(),
            new BigDecimal("100000.00"),
            new BigDecimal("10000.00"),
            new BigDecimal("90000.00")
        );
    }
}
