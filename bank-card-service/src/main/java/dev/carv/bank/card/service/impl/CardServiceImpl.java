package dev.carv.bank.card.service.impl;

import dev.carv.bank.card.constant.CardType;
import dev.carv.bank.card.dto.CardDto;
import dev.carv.bank.card.entity.CardEntity;
import dev.carv.bank.card.mapper.CardMapper;
import dev.carv.bank.card.repository.CardRepository;
import dev.carv.bank.card.service.CardService;
import dev.carv.bank.commons.exception.ResourceAlreadyExistsException;
import dev.carv.bank.commons.exception.ResourceNotFoundException;
import dev.carv.bank.commons.util.RandomEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardMapper mapper;
    private final CardRepository repository;
    private final CardMapper cardMapper;

    @Override
    public void createCard(String mobileNumber) {
        if (repository.existsByMobileNumber(mobileNumber)) {
            throw new ResourceAlreadyExistsException("Card", "mobileNumber", mobileNumber);
        }

        repository.save(createNewCard(mobileNumber));
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

    private CardEntity createNewCard(String mobileNumber) {
        var newCard = new CardEntity();
        var random = new SecureRandom();

        long randomCardNumber = random.nextLong(1_000_000_000_000_000L, 9_999_999_999_999_999L);
        int cardLimit = random.nextInt(100_000, 200_000);

        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setType(RandomEnum.obtain(CardType.class));
        newCard.setLimitAmount(new BigDecimal(cardLimit));
        newCard.setUsedAmount(new BigDecimal(0));
        newCard.setAvailableAmount(new BigDecimal(cardLimit));

        return newCard;
    }

}
