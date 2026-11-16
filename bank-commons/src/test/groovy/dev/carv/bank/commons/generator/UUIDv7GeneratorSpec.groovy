package dev.carv.bank.commons.generator

import org.hibernate.generator.EventType
import spock.lang.Specification

class UUIDv7GeneratorSpec extends Specification {

    def generator = new UUIDv7Generator()

    def "generates a UUID of version 7 for inserts"() {
        when:
        def id = generator.generate(null, new Object(), null, EventType.INSERT)

        then:
        id instanceof UUID
        (id as UUID).version() == 7
    }

    def "ignores any pre-existing value and generates a fresh one"() {
        given:
        def previous = UUID.randomUUID()

        when:
        def id = generator.generate(null, new Object(), previous, EventType.INSERT)

        then:
        id != previous
    }

    def "only fires on INSERT"() {
        expect:
        generator.eventTypes == EnumSet.of(EventType.INSERT)
    }

}
