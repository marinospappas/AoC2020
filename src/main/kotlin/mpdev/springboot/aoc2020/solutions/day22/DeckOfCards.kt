package mpdev.springboot.aoc2020.solutions.day22

import mpdev.springboot.aoc2020.utils.AocException

class DeckOfCards(input: List<String>) {

    constructor(playerCards: Array<MutableList<Int>>, gameId: Int): this(emptyList()) {
        this.player = Array(2) { Player(playerCards[it]) }
        this.gameId = gameId
    }

    lateinit var player: Array<Player>
    var round = 0
    val previousRounds = mutableMapOf<Int, Pair<Player,Player>>()
    var gameId = 0

    init {
        if (input.isNotEmpty()) {
            gameId = 1
            player = Array(2) { Player() }
            processInput(input)
        }
    }

    // part 1
    fun playRoundSimpleDeck(): Boolean {
        ++round
        val cards = IntArray(2) { player[it].dealCard() }
        val winner = cards.indexOf(cards.max())
        val loser = (winner + 1) % 2
        player[winner].takeCard(cards[winner]).takeCard(cards[loser])
        return player.none { it.hasNoCards() }
    }

    // part 2
    fun playRoundRecursiveDeck(): Boolean {
        ++round
        if (previousRounds.values.contains(Pair(player[0],player[1]))) {
            player[1].loses()
            return false
        }
        previousRounds[round] = Pair(Player(player[0].getCards().toMutableList()), Player(player[1].getCards().toMutableList()))
        val cards = IntArray(2) { player[it].dealCard() }
        val winner =
            if (player.indices.all { i -> player[i].numberOfCards() >= cards[i] }) {
                val newHand = Array(2) { player[it].sublistNCards(cards[it]).toMutableList() }
                val newDeck = DeckOfCards(newHand, gameId + 1)
                while (newDeck.playRoundRecursiveDeck()) {}
                newDeck.getWinner() - 1
            } else
                cards.indexOf(cards.max())
        val loser = (winner + 1) % 2
        player[winner].takeCard(cards[winner]).takeCard(cards[loser])
        return player.none { it.hasNoCards() }
    }

    fun getWinner() =
        if (player.none { it.hasNoCards() }) -1
        else player.indexOf(player.first { !it.hasNoCards() }) + 1

    fun getWinnersScore(): Int {
        var winnerIndex: Int
        if ((getWinner() - 1).also { winnerIndex = it } < 0)
            return -1
        return player[winnerIndex].getScore()
    }

    ///////////////////////////////////////////

    fun print() {
        println(if (round == 0) "Initial Deck"
                else "Round $round")
        (0..1).forEach {
            println("Player${it + 1}")
            println(player[it])
        }
    }

    private fun processInput(input: List<String>) {
        var playerIndx = 0
        input.forEach { line ->
            // Player 1:
            // 9
            // 2
            //
            //Player 2:
            // 5
            // 10
            try {
                when {
                    line.startsWith("Player") -> {
                        val (indexStr) = Regex("""Player (\d):""").find(line)!!.destructured
                        playerIndx = indexStr.toInt() - 1
                    }
                    line.isEmpty() -> {}
                    else -> player[playerIndx].takeCard(line.toInt())
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                throw AocException("bad input line $line")
            }
        }
    }
}
