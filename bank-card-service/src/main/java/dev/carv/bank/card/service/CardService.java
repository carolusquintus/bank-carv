package dev.carv.bank.card.service;

import dev.carv.bank.card.dto.CardDto;

public interface CardService {

    void createCard(String mobileNumber);

    CardDto fetchCard(String mobileNumber);

    boolean updateCard(CardDto dto);

    boolean deleteCard(String mobileNumber);

}
