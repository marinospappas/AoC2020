package mpdev.springboot.aoc2020.solutions.day22

data class Player_List(private val cards: MutableList<Int> = mutableListOf()) {

    fun takeCard(card: Int): Player_List {
        cards.add(card)
        return this
    }

    fun dealCard() = cards.removeFirst()

    fun hasNoCards() = cards.isEmpty()

    fun loses() {
        cards.clear()
    }

    fun numberOfCards() = cards.size

    fun sublistNCards(n: Int) = cards.subList(0, n).toList()

    fun getScore() = cards.indices.sumOf { cards[it] * (cards.size - it) }

    fun getCards() = cards.toList()

    override fun toString() = cards.toString()
}