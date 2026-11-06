package mapper;

import dev.carv.bank.loan.dto.LoanDto;
import dev.carv.bank.loan.entity.LoanEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    LoanDto toDto(LoanEntity entity);

    LoanEntity toEntity(LoanDto dto);

}
