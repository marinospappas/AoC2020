package mpdev.springboot.aoc2020.solutions.day22

import mpdev.springboot.aoc2020.utils.AocException

class DeckOfCards(input: List<String>) {

    constructor(player: Array<MutableList<Int>>, gameId: Int): this(emptyList()) {
        this.player = player
        this.gameId = gameId
    }

    lateinit var player: Array<MutableList<Int>>
    var round = 0
    val previousRounds = mutableMapOf<Int, Pair<List<Int>,List<Int>>>()
    var gameId = 0

    init {
        if (input.isNotEmpty()) {
            gameId = 1
            player = Array(2) { mutableListOf() }
            processInput(input)
        }
    }

    // part 1
    fun playRoundSimpleDeck(): Boolean {
        ++round
        val cards = IntArray(2) { player[it].removeFirst() }
        val i = cards.indexOf(cards.max())
        player[i].addAll(cards.toList().sorted().reversed())
        return player.none { it.isEmpty() }
    }

    // part 2
    fun playRoundRecursiveDeck(): Boolean {
        ++round
        if (previousRounds.values.contains(Pair(player[0],player[1]))) {
            player[1].clear()
            return false
        }
        previousRounds[round] = Pair(player[0].toList(), player[1].toList())
        val cards = IntArray(2) { player[it].removeFirst() }
        val winner =
            if (player.indices.all { i -> player[i].size >= cards[i] }) {
                val newHand = Array(2) { player[it].subList(0, cards[it]).toMutableList() }
                val newDeck = DeckOfCards(newHand, gameId + 1)
                while (newDeck.playRoundRecursiveDeck()) {}
                newDeck.getWinner() - 1
            } else
                cards.indexOf(cards.max())
        val loser = (winner + 1) % 2
        player[winner].addAll(listOf(cards[winner], cards[loser]))
        return player.none { it.isEmpty() }
    }

    fun getWinner() =
        if (player.none { it.isEmpty() }) -1
        else player.indexOf(player.first { it.isNotEmpty() }) + 1

    fun getWinnersScore(): Int {
        var winnerIndex: Int
        if ((getWinner() - 1).also { winnerIndex = it } < 0)
            return -1
        return player[winnerIndex].indices
            .sumOf { player[winnerIndex][it] * (player[winnerIndex].size - it) }
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
                    else -> player[playerIndx].add(line.toInt())
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                throw AocException("bad input line $line")
            }
        }
    }
}
