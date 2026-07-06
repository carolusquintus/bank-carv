package dev.carv.bank.account.controller;

import dev.carv.bank.account.dto.CustomerDto;
import dev.carv.bank.account.dto.ResponseDto;
import dev.carv.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static dev.carv.bank.account.constant.ResponseMessage.ACCOUNT_CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api", produces = { APPLICATION_JSON_VALUE})
public class AccountController {

    private final AccountService service;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> create(@RequestBody CustomerDto dto) {

        service.createAccount(dto);

        return ResponseEntity
            .status(ACCOUNT_CREATED.getStatus())
            .body(new ResponseDto(ACCOUNT_CREATED.getStatus().value(), ACCOUNT_CREATED.getMessage()));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber) {
        return ResponseEntity.ok(service.fetchAccount(mobileNumber));
    }

}
