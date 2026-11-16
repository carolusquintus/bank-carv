package dev.carv.bank.commons.exception

import dev.carv.bank.commons.dto.ErrorResponseDto
import dev.carv.bank.commons.dto.ValidationErrorDto
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.View
import spock.lang.Specification

import java.time.LocalDateTime

class GlobalExceptionHandlerSpec extends Specification {

    static final String PATH = "uri=/api/account"

    def webRequest = Mock(WebRequest)
    def handler = new GlobalExceptionHandler(webRequest, Stub(View))

    def setup() {
        webRequest.getDescription(false) >> PATH
    }

    def "unexpected exceptions become a 500 with the exception message"() {
        when:
        def response = handler.handleGlobalException(new IllegalStateException("kaboom"), webRequest)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        with(response.body) {
            path() == PATH
            status() == HttpStatus.INTERNAL_SERVER_ERROR
            error() == "kaboom"
            timestamp() != null
        }
    }

    def "ResourceAlreadyExistsException becomes a 400"() {
        when:
        def response = handler.handleResourceAlreadyExistsException(
            new ResourceAlreadyExistsException("Card", "mobileNumber", "123"), webRequest)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        with(response.body) {
            path() == PATH
            status() == HttpStatus.BAD_REQUEST
            error() == "Card already exists with given mobileNumber: 123"
        }
    }

    def "ResourceNotFoundException becomes a 404"() {
        when:
        def response = handler.handleResourceNotFoundException(
            new ResourceNotFoundException("Loan", "loanNumber", "42"), webRequest)

        then:
        response.statusCode == HttpStatus.NOT_FOUND
        with(response.body) {
            path() == PATH
            status() == HttpStatus.NOT_FOUND
            error() == "Loan not found with given data loanNumber: '42'"
        }
    }

    def "error timestamps are generated at handling time"() {
        given:
        def before = LocalDateTime.now()

        when:
        def response = handler.handleGlobalException(new RuntimeException("x"), webRequest)

        then:
        !response.body.timestamp().isBefore(before)
        !response.body.timestamp().isAfter(LocalDateTime.now())
    }

    def "validation failures become a 400 listing every field error"() {
        given:
        def bindingResult = new BeanPropertyBindingResult(new Object(), "customerDto")
        bindingResult.addError(new FieldError("customerDto", "name", "name can not be null or empty"))
        bindingResult.addError(new FieldError("customerDto", "email", "Email address should be a valid value"))
        def exception = new MethodArgumentNotValidException(
            new MethodParameter(String.getMethod("length"), -1), bindingResult)

        when:
        def response = handler.handleMethodArgumentNotValid(
            exception, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST

        and:
        def body = response.body as ErrorResponseDto
        body.path() == PATH
        body.status() == HttpStatus.BAD_REQUEST
        body.error() == [
            new ValidationErrorDto("name", "name can not be null or empty"),
            new ValidationErrorDto("email", "Email address should be a valid value")
        ]
    }

    def "validation failure with no errors yields an empty list"() {
        given:
        def exception = new MethodArgumentNotValidException(
            new MethodParameter(String.getMethod("length"), -1),
            new BeanPropertyBindingResult(new Object(), "customerDto"))

        when:
        def response = handler.handleMethodArgumentNotValid(
            exception, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest)

        then:
        (response.body as ErrorResponseDto).error() == []
    }

}
