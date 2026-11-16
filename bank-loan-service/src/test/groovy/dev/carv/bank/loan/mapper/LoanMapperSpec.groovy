package dev.carv.bank.loan.mapper

import dev.carv.bank.loan.constant.LoanType
import dev.carv.bank.loan.dto.LoanDto
import dev.carv.bank.loan.entity.LoanEntity
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class LoanMapperSpec extends Specification {

    LoanMapper mapper = Mappers.getMapper(LoanMapper.class)

    def "toDto should map entity to DTO correctly"() {
        given:
        def entity = new LoanEntity()
        entity.setId(UUID.randomUUID())
        entity.setMobileNumber("525512345678")
        entity.setLoanNumber("75255123456758")
        entity.setType(LoanType.CAR)
        entity.setTotalLoan(new BigDecimal("50000.00"))
        entity.setPaidLoan(new BigDecimal("5000.00"))
        entity.setOutstandingLoan(new BigDecimal("45000.00"))

        when:
        def dto = mapper.toDto(entity)

        then:
        dto != null
        dto.mobileNumber() == entity.getMobileNumber()
        dto.loanNumber() == entity.getLoanNumber()
        dto.type() == LoanType.CAR.name()
        dto.totalLoan() == entity.getTotalLoan()
        dto.paidLoan() == entity.getPaidLoan()
        dto.outstandingLoan() == entity.getOutstandingLoan()
    }

    def "toDto should return null when entity is null"() {
        expect:
        mapper.toDto(null) == null
    }

    def "toEntity should map DTO to entity correctly"() {
        given:
        def dto = new LoanDto(
            "525512345678",
            "75255123456758",
            "STUDENT",
            new BigDecimal("25000.00"),
            new BigDecimal("2500.00"),
            new BigDecimal("22500.00")
        )

        when:
        def entity = mapper.toEntity(dto)

        then:
        entity != null
        entity.getMobileNumber() == dto.mobileNumber()
        entity.getLoanNumber() == dto.loanNumber()
        entity.getType() == LoanType.STUDENT
        entity.getTotalLoan() == dto.totalLoan()
        entity.getPaidLoan() == dto.paidLoan()
        entity.getOutstandingLoan() == dto.outstandingLoan()
    }

    def "toEntity should return null when DTO is null"() {
        expect:
        mapper.toEntity(null) == null
    }
}
