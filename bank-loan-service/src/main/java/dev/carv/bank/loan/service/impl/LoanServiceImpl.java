package dev.carv.bank.loan.service.impl;

import dev.carv.bank.commons.exception.ResourceAlreadyExistsException;
import dev.carv.bank.commons.exception.ResourceNotFoundException;
import dev.carv.bank.commons.util.RandomEnum;
import dev.carv.bank.loan.repository.LoanRepository;
import dev.carv.bank.loan.constant.LoanType;
import dev.carv.bank.loan.dto.LoanDto;
import dev.carv.bank.loan.entity.LoanEntity;
import dev.carv.bank.loan.mapper.LoanMapper;
import dev.carv.bank.loan.service.LoanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanMapper mapper;
    private final LoanRepository repository;

    @Override
    public void createLoan(String mobileNumber) {
        if (repository.existsByMobileNumber(mobileNumber)) {
            throw new ResourceAlreadyExistsException("Loan", "mobileNumber", mobileNumber);
        }
        repository.save(createNewLoan(mobileNumber));
    }

    @Override
    public LoanDto fetchLoan(String mobileNumber) {
        return repository.findByMobileNumber(mobileNumber)
            .map(mapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
    }

    @Override
    @Transactional
    public boolean updateLoan(LoanDto dto) {
        var loanFound = repository.findByLoanNumber(dto.loanNumber())
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", dto.loanNumber()));

        loanFound.setMobileNumber(dto.mobileNumber());
        loanFound.setLoanNumber(dto.loanNumber());
        loanFound.setType(LoanType.valueOf(dto.type()));
        loanFound.setTotalLoan(dto.totalLoan());
        loanFound.setPaidLoan(dto.paidLoan());
        loanFound.setOutstandingLoan(dto.outstandingLoan());

        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        var loan = repository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        repository.deleteById(loan.getId());
        return true;
    }

    private LoanEntity createNewLoan(String mobileNumber) {
        var newLoan = new LoanEntity();
        var random = new SecureRandom();

        long randomLoanNumber = random.nextLong(10_000_000_000_000L, 99_999_999_999_999L);
        int loanLimit = random.nextInt(100_000, 200_000);

        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setType(RandomEnum.obtain(LoanType.class));
        newLoan.setTotalLoan(new BigDecimal(loanLimit));
        newLoan.setPaidLoan(new BigDecimal(0));
        newLoan.setOutstandingLoan(new BigDecimal(loanLimit));

        return newLoan;
    }

}
