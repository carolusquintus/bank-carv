package dev.carv.bank.loan.api;

import dev.carv.bank.commons.dto.ErrorResponseDto;
import dev.carv.bank.commons.dto.ResponseDto;
import dev.carv.bank.loan.dto.LoanDto;
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

import static dev.carv.bank.commons.constant.ValidationConstants.MOBILE_NUMBER_REGEX;

@Tag(
    name = "CRUD Loan API for Bank CARV",
    description = "API for managing loan details"
)
public interface LoanAPI {

    @Operation(
        summary = "Create loan endpoint",
        description = "REST operation to create a loan inside Bank CARV",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Loan Created"
            )
        }
    )
    ResponseEntity<ResponseDto> createLoan(@RequestParam
                                           @Pattern(regexp = MOBILE_NUMBER_REGEX, message = "mobileNumber must be 12 digits")
                                           String mobileNumber);

    @Operation(
        summary = "Fetch loan endpoint",
        description = "REST operation to fetch loan details based on a mobile number",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Loan OK"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Loan Not Found"
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
    ResponseEntity<LoanDto> fetchLoan(@RequestParam
                                      @Pattern(regexp = MOBILE_NUMBER_REGEX, message = "mobileNumber must be 12 digits")
                                      String mobileNumber);

    @Operation(
        summary = "Update loan endpoint",
        description = "REST operation to update loan details based on a loan number",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Loan Updated"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Loan Not Found"
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
    ResponseEntity<ResponseDto> updateLoan(@Valid
                                           @RequestBody
                                           LoanDto dto);


    @Operation(
        summary = "Delete loan endpoint",
        description = "REST operation to delete loan details based on a mobile number",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Loan Deleted"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Loan Not Found"
            ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    ResponseEntity<ResponseDto> deleteLoan(@RequestParam
                                           @Pattern(regexp = MOBILE_NUMBER_REGEX, message = "mobileNumber must be 12 digits")
                                           String mobileNumber);
}
