package dev.carv.bank.commons.entity

import spock.lang.Specification

import java.time.LocalDateTime

class AuditEntitySpec extends Specification {

    /** AuditEntity is abstract, so we need a concrete stand-in. */
    static class Audited extends AuditEntity {}

    def "a new entity has no audit data yet"() {
        given:
        def entity = new Audited()

        expect:
        entity.createdAt == null
        entity.updatedAt == null
        entity.createdBy == null
        entity.updatedBy == null
    }

    def "audit fields round-trip through their accessors"() {
        given:
        def created = LocalDateTime.of(2026, 1, 2, 3, 4, 5)
        def updated = created.plusDays(1)
        def entity = new Audited()

        when:
        entity.setCreatedAt(created)
        entity.setUpdatedAt(updated)
        entity.setCreatedBy("bank-account-service")
        entity.setUpdatedBy("bank-card-service")

        then:
        entity.getCreatedAt() == created
        entity.getUpdatedAt() == updated
        entity.getCreatedBy() == "bank-account-service"
        entity.getUpdatedBy() == "bank-card-service"
    }

    def "toString lists every audit field"() {
        given:
        def created = LocalDateTime.of(2026, 1, 2, 3, 4, 5)
        def entity = new Audited(createdAt: created, createdBy: "me")

        expect:
        entity.toString() == "AuditEntity(createdAt=$created, updatedAt=null, createdBy=me, updatedBy=null)"
    }

}
