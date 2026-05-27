package dev.carv.bank.account.mapper;

import dev.carv.bank.account.dto.CustomerDto;
import dev.carv.bank.account.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toDto(CustomerEntity entity);

    CustomerEntity toEntity(CustomerDto dto);

}
