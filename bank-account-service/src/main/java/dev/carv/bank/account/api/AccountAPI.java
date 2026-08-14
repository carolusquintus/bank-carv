package dev.carv.bank.account.api;

import dev.carv.bank.account.dto.CustomerDto;
import dev.carv.bank.account.dto.ErrorResponseDto;
import dev.carv.bank.account.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(
    name = "CRUD Account API for Bank CARV",
    description = "API for managing customer account details"
)
public interface AccountAPI {

    @Operation(
        summary = "Create account endpoint",
        description = "REST operation to create a new customer and account inside Bank CARV",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Account Created"
            )
        }
    )
    ResponseEntity<ResponseDto> createAccount(CustomerDto dto);

    @Operation(
        summary = "Update account endpoint",
        description = "REST operation to update customer and account details based on a account number",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Account Updated"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Account Not Found"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            )
        }
    )
    ResponseEntity<ResponseDto> updateAccount(CustomerDto dto);

    @Operation(
        summary = "Delete account endpoint",
        description = "REST operation to delete customer and account details based on a mobile number",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Account Deleted"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Account Not Found"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    ResponseEntity<CustomerDto> fetchAccount(String mobileNumber);

    @Operation(
        summary = "Fetch account endpoint",
        description = "REST operation to fetch customer and account details based on a mobile number",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Account Deleted"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Account Not Found"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            )
        }
    )
    ResponseEntity<ResponseDto> deleteAccount(String mobileNumber);

}
