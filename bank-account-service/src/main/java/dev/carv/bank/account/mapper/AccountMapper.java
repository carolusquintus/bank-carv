package dev.carv.bank.account.mapper;

import dev.carv.bank.account.dto.AccountDto;
import dev.carv.bank.account.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toDto(AccountEntity entity);

    AccountEntity toEntity(AccountDto dto);

}
