package dev.carv.bank.loan.dto

import spock.lang.Specification

class LoanDtoSpec extends Specification {

    def "LoanDto record should hold correct values and implement equals/hashCode/toString"() {
        given:
        def dto1 = new LoanDto(
            "525512345678",
            "75255123456758",
            "HOME",
            new BigDecimal("100000.00"),
            new BigDecimal("10000.00"),
            new BigDecimal("90000.00")
        )
        def dto2 = new LoanDto(
            "525512345678",
            "75255123456758",
            "HOME",
            new BigDecimal("100000.00"),
            new BigDecimal("10000.00"),
            new BigDecimal("90000.00")
        )
        def dto3 = new LoanDto(
            "525500000000",
            "75255000000000",
            "PERSONAL",
            new BigDecimal("20000.00"),
            new BigDecimal("0.00"),
            new BigDecimal("20000.00")
        )

        expect:
        dto1.mobileNumber() == "525512345678"
        dto1.loanNumber() == "75255123456758"
        dto1.type() == "HOME"
        dto1.totalLoan() == new BigDecimal("100000.00")
        dto1.paidLoan() == new BigDecimal("10000.00")
        dto1.outstandingLoan() == new BigDecimal("90000.00")

        dto1 == dto2
        dto1.hashCode() == dto2.hashCode()
        dto1 != dto3
        dto1.toString().contains("525512345678")
        dto1.toString().contains("75255123456758")
        dto1.toString().contains("HOME")
    }
}
