package dev.carv.bank.loan.entity

import dev.carv.bank.loan.constant.LoanType
import spock.lang.Specification

class LoanEntitySpec extends Specification {

    def "LoanEntity should have working getters, setters, equals, hashCode, and toString"() {
        given:
        def id = UUID.randomUUID()
        def entity1 = new LoanEntity()
        entity1.setId(id)
        entity1.setMobileNumber("525512345678")
        entity1.setLoanNumber("75255123456758")
        entity1.setType(LoanType.CAR)
        entity1.setTotalLoan(new BigDecimal("50000.00"))
        entity1.setPaidLoan(new BigDecimal("5000.00"))
        entity1.setOutstandingLoan(new BigDecimal("45000.00"))

        def entity2 = new LoanEntity()
        entity2.setId(UUID.randomUUID())

        expect:
        entity1.getId() == id
        entity1.getMobileNumber() == "525512345678"
        entity1.getLoanNumber() == "75255123456758"
        entity1.getType() == LoanType.CAR
        entity1.getTotalLoan() == new BigDecimal("50000.00")
        entity1.getPaidLoan() == new BigDecimal("5000.00")
        entity1.getOutstandingLoan() == new BigDecimal("45000.00")

        // LoanEntity extends AuditEntity which doesn't implement equals, so callSuper=true uses Object.equals
        entity1 == entity1
        entity1.hashCode() == entity1.hashCode()
        entity1 != entity2
        entity1 != null
        entity1 != new Object()
        entity1.canEqual(entity2) == true
        entity1.toString().contains("525512345678")
        entity1.toString().contains("75255123456758")
    }
}
