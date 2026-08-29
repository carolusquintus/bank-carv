package dev.carv.bank.account.api;

import dev.carv.bank.account.dto.CustomerDto;
import dev.carv.bank.account.dto.ErrorResponseDto;
import dev.carv.bank.account.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static dev.carv.bank.account.constant.ValidationConstants.MOBILE_NUMBER_REGEX;

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
    ResponseEntity<ResponseDto> createAccount(@Valid
                                              @RequestBody
                                              CustomerDto dto);

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
    ResponseEntity<ResponseDto> updateAccount(@Valid
                                              @RequestBody
                                              CustomerDto dto);

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
    ResponseEntity<CustomerDto> fetchAccount(@RequestParam
                                             @Pattern(regexp = MOBILE_NUMBER_REGEX, message = "mobileNumber must be 12 digits")
                                             String mobileNumber);

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
    ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                              @Pattern(regexp = MOBILE_NUMBER_REGEX, message = "mobileNumber must be 12 digits")
                                              String mobileNumber);

}
