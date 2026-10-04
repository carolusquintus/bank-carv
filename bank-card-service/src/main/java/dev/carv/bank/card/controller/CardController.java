package dev.carv.bank.card.controller;

import dev.carv.bank.card.api.CardAPI;
import dev.carv.bank.card.dto.CardDto;
import dev.carv.bank.card.service.CardService;
import dev.carv.bank.commons.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/card", produces = APPLICATION_JSON_VALUE)
public class CardController implements CardAPI {

    private final CardService service;

    @Override
    public ResponseEntity<ResponseDto> createCard(CardDto dto) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> updateCard(CardDto dto) {
        return null;
    }

    @Override
    public ResponseEntity<CardDto> fetchCard(String mobileNumber) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> deleteCard(String mobileNumber) {
        return null;
    }

}
