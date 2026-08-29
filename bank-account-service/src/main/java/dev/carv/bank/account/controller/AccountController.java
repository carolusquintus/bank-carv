package dev.carv.bank.account.controller;

import dev.carv.bank.account.api.AccountAPI;
import dev.carv.bank.account.dto.CustomerDto;
import dev.carv.bank.account.dto.ResponseDto;
import dev.carv.bank.account.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static dev.carv.bank.account.constant.ResponseMessage.*;
import static dev.carv.bank.account.constant.ValidationConstants.MOBILE_NUMBER_REGEX;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/account", produces = { APPLICATION_JSON_VALUE})
public class AccountController implements AccountAPI {

    private final AccountService service;

    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(CustomerDto dto) {

        service.createAccount(dto);

        return ResponseEntity
            .status(ACCOUNT_CREATED.getStatus())
            .body(new ResponseDto(ACCOUNT_CREATED.getStatus().value(), ACCOUNT_CREATED.getMessage()));
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateAccount(CustomerDto dto) {
        if (service.updateAccount(dto)) {
            return ResponseEntity.ok(new ResponseDto(SUCCESS.getStatus().value(), SUCCESS.getMessage()));
        }
        return ResponseEntity
            .internalServerError().body(new ResponseDto(INTERNAL_ERROR.getStatus().value(), INTERNAL_ERROR.getMessage()));
    }

    @GetMapping
    public ResponseEntity<CustomerDto> fetchAccount(String mobileNumber) {
        return ResponseEntity.ok(service.fetchAccount(mobileNumber));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteAccount(String mobileNumber) {
        if (service.deleteAccount(mobileNumber)) {
            return ResponseEntity.ok(new ResponseDto(SUCCESS.getStatus().value(), SUCCESS.getMessage()));
        }
        return ResponseEntity
            .internalServerError().body(new ResponseDto(INTERNAL_ERROR.getStatus().value(), INTERNAL_ERROR.getMessage()));

    }

}
