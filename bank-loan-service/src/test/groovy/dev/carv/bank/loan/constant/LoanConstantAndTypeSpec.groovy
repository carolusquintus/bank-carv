package dev.carv.bank.loan.constant

import spock.lang.Specification

class LoanConstantAndTypeSpec extends Specification {

    def "LoanConstant should contain valid column and table name definitions"() {
        expect:
        LoanConstant.LOAN == "LOAN"
        LoanConstant.ID == "ID"
        LoanConstant.MOBILE_NUMBER == "MOBILE_NUMBER"
        LoanConstant.LOAN_NUMBER == "LOAN_NUMBER"
        LoanConstant.TYPE == "TYPE"
        LoanConstant.TOTAL_LOAN == "TOTAL_LOAN"
        LoanConstant.PAID_LOAN == "PAID_LOAN"
        LoanConstant.OUTSTANDING_LOAN == "OUTSTANDING_LOAN"

        and:
        new LoanConstant() != null
    }

    def "LoanType enum should contain expected values and valueOf should work"() {
        expect:
        LoanType.values() == [LoanType.HOME, LoanType.CAR, LoanType.PERSONAL, LoanType.STUDENT] as LoanType[]
        LoanType.valueOf("HOME") == LoanType.HOME
        LoanType.valueOf("CAR") == LoanType.CAR
        LoanType.valueOf("PERSONAL") == LoanType.PERSONAL
        LoanType.valueOf("STUDENT") == LoanType.STUDENT
    }
}
