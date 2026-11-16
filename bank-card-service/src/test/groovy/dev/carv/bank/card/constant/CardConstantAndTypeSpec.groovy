package dev.carv.bank.card.constant

import spock.lang.Specification

class CardConstantAndTypeSpec extends Specification {

    def "CardConstant should contain valid column and table name definitions"() {
        expect:
        CardConstant.CARD == "CARD"
        CardConstant.ID == "ID"
        CardConstant.MOBILE_NUMBER == "MOBILE_NUMBER"
        CardConstant.CARD_NUMBER == "CARD_NUMBER"
        CardConstant.TYPE == "TYPE"
        CardConstant.LIMIT_AMOUNT == "LIMIT_AMOUNT"
        CardConstant.USED_AMOUNT == "USED_AMOUNT"
        CardConstant.AVAILABLE_AMOUNT == "AVAILABLE_AMOUNT"

        and:
        new CardConstant() != null
    }

    def "CardType enum should contain expected values and valueOf should work"() {
        expect:
        CardType.values() == [CardType.DEBIT, CardType.CREDIT] as CardType[]
        CardType.valueOf("DEBIT") == CardType.DEBIT
        CardType.valueOf("CREDIT") == CardType.CREDIT
    }
}
