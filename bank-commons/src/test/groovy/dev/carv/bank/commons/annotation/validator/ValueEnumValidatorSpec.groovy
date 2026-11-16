package dev.carv.bank.commons.annotation.validator

import dev.carv.bank.commons.annotation.ValueEnum
import jakarta.validation.ConstraintValidatorContext
import spock.lang.Specification
import spock.lang.Unroll

class ValueEnumValidatorSpec extends Specification {

    enum Color { RED, GREEN, BLUE }

    def context = Mock(ConstraintValidatorContext)
    def validator = new ValueEnumValidator()

    def setup() {
        validator.initialize(Stub(ValueEnum) { clazz() >> Color })
    }

    @Unroll
    def "'#value' is valid -> #expected"() {
        expect:
        validator.isValid(value, context) == expected

        where:
        value   || expected
        "RED"   || true
        "GREEN" || true
        "BLUE"  || true
        null    || true     // null is left to @NotNull
        "red"   || false    // case sensitive
        "PINK"  || false
        ""      || false
        " RED"  || false
    }

}
