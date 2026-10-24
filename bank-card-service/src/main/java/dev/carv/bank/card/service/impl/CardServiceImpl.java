package dev.carv.bank.card.service.impl;

import dev.carv.bank.card.constant.CardType;
import dev.carv.bank.card.dto.CardDto;
import dev.carv.bank.card.mapper.CardMapper;
import dev.carv.bank.card.repository.CardRepository;
import dev.carv.bank.card.service.CardService;
import dev.carv.bank.commons.exception.ResourceAlreadyExistsException;
import dev.carv.bank.commons.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardMapper mapper;
    private final CardRepository repository;
    private final CardMapper cardMapper;

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
        return repository.findByMobileNumber(mobileNumber)
            .map(cardMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
    }

    @Override
    @Transactional
    public boolean updateCard(CardDto dto) {
        var cardFound = repository.findByCardNumber(dto.cardNumber())
            .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", dto.cardNumber()));

        cardFound.setMobileNumber(dto.mobileNumber());
        cardFound.setCardNumber(dto.cardNumber());
        cardFound.setType(CardType.valueOf(dto.type()));
        cardFound.setLimitAmount(dto.limitAmount());
        cardFound.setAvailableAmount(dto.availableAmount());
        cardFound.setUsedAmount(dto.usedAmount());

        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        var customer = repository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        repository.deleteById(customer.getId());
        return true;
    }

}
