package dev.carv.bank.card.service.impl;

import dev.carv.bank.card.dto.CardDto;
import dev.carv.bank.card.mapper.CardMapper;
import dev.carv.bank.card.repository.CardRepository;
import dev.carv.bank.card.service.CardService;
import dev.carv.bank.commons.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static dev.carv.bank.commons.constant.Message.RESOURCE_ALREADY_EXISTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardMapper mapper;
    private final CardRepository repository;

    @Override
    public void createCard(CardDto dto) {
        var card = mapper.toEntity(dto);

        if (repository.existsByMobileNumber(card.getMobileNumber())) {
            throw new ResourceAlreadyExistsException("Card", "mobileNumber", card.getMobileNumber());
        }

        repository.save(card);
    }

    @Override
    public CardDto fetchCard(String mobileNumber) {
        return null;
    }

    @Override
    public boolean updateCard(CardDto dto) {
        return false;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        return false;
    }

}
