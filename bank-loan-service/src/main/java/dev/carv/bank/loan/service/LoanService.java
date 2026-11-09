package dev.carv.bank.loan.service;

import dev.carv.bank.loan.dto.LoanDto;

public interface LoanService {

    void createLoan(String mobileNumber);

    LoanDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoanDto dto);

    boolean deleteLoan(String mobileNumber);

}
