package mpdev.springboot.aoc2020.solutions.day22

data class Player(private val cards: MutableList<Int> = mutableListOf()) {

    fun dealCard() = cards.removeFirst()

    fun takeCard(card: Int): Player {
        cards.add(card)
        return this
    }

    fun hasNoCards() = cards.isEmpty()

    fun loses() {
        cards.clear()
    }

    fun numberOfCards() = cards.size

    fun sublistNCards(n: Int) = cards.subList(0,n)

    fun getScore() =
        cards.indices.sumOf { cards[it] * (cards.size - it) }

    fun getCards() = cards.toList()

    override fun toString() = cards.toString()
}