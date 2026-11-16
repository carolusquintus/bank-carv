package dev.carv.bank.commons.util

import spock.lang.Specification

import java.lang.reflect.InvocationTargetException

class UUIDv7Spec extends Specification {

    def "generated UUID has version 7 and RFC 9562 variant"() {
        when:
        def uuid = UUIDv7.generate()

        then:
        uuid.version() == 7
        uuid.variant() == 2
    }

    def "embedded 48-bit timestamp falls between the surrounding clock reads"() {
        given:
        long before = System.currentTimeMillis()

        when:
        def uuid = UUIDv7.generate()
        long after = System.currentTimeMillis()

        and: "the timestamp lives in the top 48 bits of the most significant long"
        long embedded = uuid.mostSignificantBits >>> 16

        then:
        embedded >= before
        embedded <= after
    }

    def "UUIDs are unique across many generations"() {
        when:
        def uuids = (1..5_000).collect { UUIDv7.generate() } as Set

        then:
        uuids.size() == 5_000
    }

    def "UUIDs generated later sort at or after earlier ones by timestamp"() {
        given:
        def first = UUIDv7.generate()
        Thread.sleep(5)

        when:
        def second = UUIDv7.generate()

        then:
        (second.mostSignificantBits >>> 16) > (first.mostSignificantBits >>> 16)
    }

    def "utility class refuses to be instantiated"() {
        given:
        def constructor = UUIDv7.getDeclaredConstructor()
        constructor.accessible = true

        when:
        constructor.newInstance()

        then:
        def e = thrown(InvocationTargetException)
        e.cause instanceof UnsupportedOperationException
    }

}
