package dev.carv.bank.card.api;

import dev.carv.bank.card.dto.CardDto;
import dev.carv.bank.commons.dto.ErrorResponseDto;
import dev.carv.bank.commons.dto.ResponseDto;
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
    name = "CRUD Card API for Bank CARV",
    description = "API for managing card details"
)
public interface CardAPI {

    @Operation(
        summary = "Create card endpoint",
        description = "REST operation to create a new card inside Bank CARV",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Card Created"
            )
        }
    )
    ResponseEntity<ResponseDto> createCard(@Valid
                                           @RequestBody
                                           CardDto dto);

    @Operation(
        summary = "Update card endpoint",
        description = "REST operation to update card details based on a card number",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Card Updated"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Card Not Found"
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
    ResponseEntity<ResponseDto> updateCard(@Valid
                                           @RequestBody
                                           CardDto dto);


    @Operation(
        summary = "Fetch card endpoint",
        description = "REST operation to fetch card details based on a mobile number",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Card OK"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Card Not Found"
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
    ResponseEntity<CardDto> fetchCard(@RequestParam
                                      @Pattern(regexp = MOBILE_NUMBER_REGEX, message = "mobileNumber must be 12 digits")
                                      String mobileNumber);

    @Operation(
        summary = "Delete card endpoint",
        description = "REST operation to delete card details based on a mobile number",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Card Deleted"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Card Not Found"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    ResponseEntity<ResponseDto> deleteCard(@RequestParam
                                           @Pattern(regexp = MOBILE_NUMBER_REGEX, message = "mobileNumber must be 12 digits")
                                           String mobileNumber);

}
