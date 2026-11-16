package dev.carv.bank.account.constant

import spock.lang.Specification

class AccountColumnsAndTypeSpec extends Specification {

    def "AccountColumns should define correct table and column names"() {
        expect:
        AccountColumns.CUSTOMER == "CUSTOMER"
        AccountColumns.ID == "ID"
        AccountColumns.NAME == "NAME"
        AccountColumns.EMAIL == "EMAIL"
        AccountColumns.MOBILE_NUMBER == "MOBILE_NUMBER"
        AccountColumns.ACCOUNT == "ACCOUNT"
        AccountColumns.CUSTOMER_ID == "CUSTOMER_ID"
        AccountColumns.ACCOUNT_NUMBER == "ACCOUNT_NUMBER"
        AccountColumns.TYPE == "TYPE"
        AccountColumns.BRANCH_ADDRESS == "BRANCH_ADDRESS"

        and:
        new AccountColumns() != null
    }

    def "AccountType should contain SAVINGS and valueOf should work"() {
        expect:
        AccountType.values() == [AccountType.SAVINGS] as AccountType[]
        AccountType.valueOf("SAVINGS") == AccountType.SAVINGS
    }
}
