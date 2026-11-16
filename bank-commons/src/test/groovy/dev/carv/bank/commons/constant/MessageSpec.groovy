package dev.carv.bank.commons.constant

import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll

class MessageSpec extends Specification {

    @Unroll
    def "#message maps to HTTP #status"() {
        expect:
        message.status == status

        where:
        message                          || status
        Message.SUCCESS                  || HttpStatus.OK
        Message.RESOURCE_CREATED         || HttpStatus.CREATED
        Message.RESOURCE_ALREADY_EXISTS  || HttpStatus.BAD_REQUEST
        Message.RESOURCE_VALIDATION      || HttpStatus.BAD_REQUEST
        Message.RESOURCE_NOT_FOUND       || HttpStatus.NOT_FOUND
        Message.INTERNAL_ERROR           || HttpStatus.INTERNAL_SERVER_ERROR
    }

    @Unroll
    def "#message formats #params into '#expected'"() {
        expect:
        message.format(*params) == expected

        where:
        message                          | params                                || expected
        Message.RESOURCE_CREATED         | ["Account"]                           || "Account created successfully"
        Message.RESOURCE_ALREADY_EXISTS  | ["Card", "mobileNumber", "123"]       || "Card already exists with given mobileNumber: 123"
        Message.RESOURCE_NOT_FOUND       | ["Loan", "loanNumber", "999"]         || "Loan not found with given data loanNumber: '999'"
        Message.RESOURCE_VALIDATION      | ["CardDto"]                           || "CardDto has validation errors"
    }

    def "format without params returns the raw text untouched"() {
        expect:
        Message.SUCCESS.format() == Message.SUCCESS.text
        Message.RESOURCE_CREATED.format() == "%s created successfully"
    }

    def "every message carries a non-blank text"() {
        expect:
        Message.values().every { it.text?.trim() }
    }

}
