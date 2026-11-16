package dev.carv.bank.commons.dto

import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import spock.lang.Specification

class ValidationErrorDtoSpec extends Specification {

    def "copies field name and default message out of a FieldError"() {
        given:
        def error = new FieldError("customerDto", "email", "Email address should be a valid value")

        when:
        def dto = new ValidationErrorDto(error)

        then:
        dto.field() == "email"
        dto.message() == "Email address should be a valid value"
    }

    def "a plain ObjectError (no field) is not supported by the convenience constructor"() {
        when:
        new ValidationErrorDto(new ObjectError("customerDto", "class-level problem"))

        then:
        thrown(ClassCastException)
    }

}
