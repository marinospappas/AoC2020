package mpdev.springboot.aoc2020.solutions.day22

import java.math.BigInteger

data class Player_BitMap(private var cards: BigInteger = BigInteger.ZERO) {

    companion object {
        const val BITS_PER_CARD = 8
    }

    private var cardIndex = 0

    init {
        var workingCards = cards
        while (workingCards > BigInteger.ZERO) {
            ++cardIndex
            workingCards = workingCards.shr(BITS_PER_CARD)
        }
    }

    fun takeCard(card: Int): Player_BitMap {
        val newCard = BigInteger.valueOf(card.toLong()).shiftLeft(cardIndex * BITS_PER_CARD)
        cards = cards.or(newCard)
        ++cardIndex
        return this
    }

    fun dealCard(): Int {
        val card = cards.toInt().and(0xFF)
        cards = cards.shr(BITS_PER_CARD)
        --cardIndex
        return card
    }

    fun hasNoCards() = cards == BigInteger.ZERO

    fun loses() {
        cards = BigInteger.ZERO
        cardIndex = 0
    }

    fun numberOfCards() = cardIndex

    fun sublistNCards(n: Int): BigInteger {
        var mask = BigInteger.valueOf(0x0)
        (1..n).forEach { _ -> mask = mask.shl(BITS_PER_CARD).or(BigInteger.valueOf(0xFF)) }
        return cards.and(mask)
    }

    fun getScore(): Int {
        val cardsList = mutableListOf<Int>()
        var workingCards = cards
        while (workingCards > BigInteger.ZERO) {
            val card = workingCards.toInt().and(0xFF)
            workingCards = workingCards.shr(BITS_PER_CARD)
            cardsList.add(card)
        }
        return cardsList.indices.sumOf { cardsList[it] * (cardsList.size - it) }
    }

    fun getCards() = cards

    fun cardsToList(): List<Int> {
        var thisCards = cards
        val cardsList = mutableListOf<Int>()
        (0 until cardIndex).forEach { _ ->
            cardsList.add(thisCards.and(BigInteger.valueOf(0xFF)).toInt())
            thisCards = thisCards.shr(BITS_PER_CARD)
        }
        return cardsList
    }

    override fun toString() = cardsToList().toString()
}