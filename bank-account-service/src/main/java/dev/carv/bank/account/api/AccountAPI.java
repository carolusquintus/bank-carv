package dev.carv.bank.account.api;

import dev.carv.bank.account.dto.CustomerDto;
import dev.carv.bank.account.dto.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(
    name = "CRUD Account API for Bank CARV",
    description = "API for managing customer account details"
)
public interface AccountAPI {

    ResponseEntity<ResponseDto> createAccount(CustomerDto dto);

    ResponseEntity<ResponseDto> updateAccount(CustomerDto dto);

    ResponseEntity<CustomerDto> fetchAccount(String mobileNumber);

    ResponseEntity<ResponseDto> deleteAccount(String mobileNumber);

}
