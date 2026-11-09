package dev.carv.bank.loan.controller;

import dev.carv.bank.commons.dto.ResponseDto;
import dev.carv.bank.loan.api.LoanAPI;
import dev.carv.bank.loan.dto.LoanDto;
import dev.carv.bank.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static dev.carv.bank.commons.constant.Message.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/loan", produces = APPLICATION_JSON_VALUE)
public class LoanController implements LoanAPI {

    private final LoanService service;

    @PostMapping
    public ResponseEntity<ResponseDto> createLoan(String mobileNumber) {

        service.createLoan(mobileNumber);

        return ResponseEntity
            .status(RESOURCE_CREATED.getStatus())
            .body(new ResponseDto(RESOURCE_CREATED.getStatus(), RESOURCE_CREATED.format("Loan")));
    }

    @GetMapping
    public ResponseEntity<LoanDto> fetchLoan(String mobileNumber) {
        return ResponseEntity.ok(service.fetchLoan(mobileNumber));
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateLoan(LoanDto dto) {
        if (service.updateLoan(dto)) {
            return ResponseEntity.ok(new ResponseDto(SUCCESS.getStatus(), SUCCESS.getText()));
        }
        return ResponseEntity
            .internalServerError().body(new ResponseDto(INTERNAL_ERROR.getStatus(), INTERNAL_ERROR.getText()));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteLoan(String mobileNumber) {
        if (service.deleteLoan(mobileNumber)) {
            return ResponseEntity.ok(new ResponseDto(SUCCESS.getStatus(), SUCCESS.getText()));
        }
        return ResponseEntity
            .internalServerError().body(new ResponseDto(INTERNAL_ERROR.getStatus(), INTERNAL_ERROR.getText()));
    }

}
