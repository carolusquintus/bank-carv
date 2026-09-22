package dev.carv.bank.account.controller;

import dev.carv.bank.account.api.AccountAPI;
import dev.carv.bank.account.dto.CustomerDto;
import dev.carv.bank.commons.dto.ResponseDto;
import dev.carv.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static dev.carv.bank.commons.constant.ResponseMessage.*;
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
            .status(RESOURCE_CREATED.getStatus())
            .body(new ResponseDto(RESOURCE_CREATED.getStatus().value(), RESOURCE_CREATED.getMessage().formatted("Account")));
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
