package dev.carv.bank.commons.util

import spock.lang.Specification

class RandomEnumSpec extends Specification {

    enum Flavor { VANILLA, CHOCOLATE, STRAWBERRY }

    enum Lonely { ONLY }

    def "always returns a constant that belongs to the enum"() {
        expect:
        (1..200).every { RandomEnum.obtain(Flavor) in Flavor.values().toList() }
    }

    def "single-constant enum always yields that constant"() {
        expect:
        (1..20).every { RandomEnum.obtain(Lonely) == Lonely.ONLY }
    }

    def "eventually produces every constant (not stuck on one)"() {
        when:
        def seen = (1..500).collect { RandomEnum.obtain(Flavor) } as Set

        then: "500 draws from 3 values missing one has odds of ~1e-88, so this is safe"
        seen == Flavor.values().toList() as Set
    }

}
