package dev.carv.bank.commons.exception

import dev.carv.bank.commons.constant.Message
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import spock.lang.Specification

class ResourceExceptionsSpec extends Specification {

    def "ResourceNotFoundException builds a message from the default template"() {
        when:
        def e = new ResourceNotFoundException("Customer", "mobileNumber", "525512345678")

        then:
        e.message == "Customer not found with given data mobileNumber: '525512345678'"
        e instanceof RuntimeException
    }

    def "ResourceNotFoundException accepts a custom Message template"() {
        when:
        def e = new ResourceNotFoundException(Message.RESOURCE_CREATED, "Thing")

        then:
        e.message == "Thing created successfully"
    }

    def "ResourceNotFoundException is annotated with 404"() {
        expect:
        ResourceNotFoundException.getAnnotation(ResponseStatus).value() == HttpStatus.NOT_FOUND
    }

    def "ResourceAlreadyExistsException builds a message from the default template"() {
        when:
        def e = new ResourceAlreadyExistsException("Card", "mobileNumber", "525512345678")

        then:
        e.message == "Card already exists with given mobileNumber: 525512345678"
        e instanceof RuntimeException
    }

    def "ResourceAlreadyExistsException accepts a custom Message template"() {
        when:
        def e = new ResourceAlreadyExistsException(Message.RESOURCE_VALIDATION, "Dto")

        then:
        e.message == "Dto has validation errors"
    }

    def "ResourceAlreadyExistsException is annotated with 400"() {
        expect:
        ResourceAlreadyExistsException.getAnnotation(ResponseStatus).value() == HttpStatus.BAD_REQUEST
    }

}
