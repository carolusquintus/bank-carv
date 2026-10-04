package dev.carv.bank.card.mapper;

import dev.carv.bank.card.dto.CardDto;
import dev.carv.bank.card.entity.CardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDto toDto(CardEntity entity);

    CardEntity toEntity(CardDto dto);

}
