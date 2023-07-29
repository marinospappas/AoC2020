package mpdev.springboot.aoc2020.solutions.day22

import mpdev.springboot.aoc2020.utils.AocException

class DeckOfCards(input: List<String>) {

    val player: Array<MutableList<Int>> = Array(2) { mutableListOf<Int>() }
    var round = 0

    init {
        processInput(input)
    }

    fun playRound(): Boolean {
        ++round
        val cards = IntArray(2) { player[it].removeFirst() }
        val i = cards.indexOf(cards.max())
        player[i].addAll(cards.toList().sorted().reversed())
        return player.none { it.isEmpty() }
    }

    fun getWinner() =
        if (player.none { it.isEmpty() }) -1
        else player.indexOf(player.first { it.isNotEmpty() }) + 1

    fun getWinnersScore(): Long {
        var winnerIndex: Int
        if ((getWinner() - 1).also { winnerIndex = it } < 0)
            return -1
        return player[winnerIndex].indices
            .sumOf { player[winnerIndex][it] * (player[winnerIndex].size - it) }.toLong()
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
