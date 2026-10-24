package dev.carv.bank.card.controller;

import dev.carv.bank.card.api.CardAPI;
import dev.carv.bank.card.dto.CardDto;
import dev.carv.bank.card.service.CardService;
import dev.carv.bank.commons.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static dev.carv.bank.commons.constant.Message.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/card", produces = APPLICATION_JSON_VALUE)
public class CardController implements CardAPI {

    private final CardService service;

    @PostMapping
    public ResponseEntity<ResponseDto> createCard(CardDto dto) {

        service.createCard(dto);

        return ResponseEntity
            .status(RESOURCE_CREATED.getStatus())
            .body(new ResponseDto(RESOURCE_CREATED.getStatus().value(), RESOURCE_CREATED.format("Card")));
    }

    @GetMapping
    public ResponseEntity<CardDto> fetchCard(String mobileNumber) {
        return ResponseEntity.ok(service.fetchCard(mobileNumber));
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateCard(CardDto dto) {
        if (service.updateCard(dto)) {
            return ResponseEntity.ok(new ResponseDto(SUCCESS.getStatus().value(), SUCCESS.getText()));
        }
        return ResponseEntity
            .internalServerError().body(new ResponseDto(INTERNAL_ERROR.getStatus().value(), INTERNAL_ERROR.getText()));
    }

    @Override
    public ResponseEntity<ResponseDto> deleteCard(String mobileNumber) {
        return null;
    }

}
