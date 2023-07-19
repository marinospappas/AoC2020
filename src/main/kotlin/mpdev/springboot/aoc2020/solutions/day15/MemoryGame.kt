package mpdev.springboot.aoc2020.solutions.day15

class MemoryGame(input: List<String>) {

    val startSequence = input[0].split(",").map { it.toInt() }
    var round = 0
    val numbersIndex = startSequence.associateWith { NumbersIndex() }.toMutableMap()
    var lastSpoken = -1

    fun playRound(): Int {
        var nextNumber: Int
        if (round < startSequence.size) {
            nextNumber = startSequence[round]
        }
        else {
            nextNumber = lastSpoken
            if (numbersIndex[nextNumber]!!.timesSpoken == 1)
                nextNumber = 0
            else
                nextNumber = numbersIndex[nextNumber]!!.lastRoundSpoken - numbersIndex[nextNumber]!!.prevRoundSpoken
        }
        if (numbersIndex[nextNumber] == null)
            numbersIndex[nextNumber] = NumbersIndex()
        numbersIndex[nextNumber]!!.prevRoundSpoken = numbersIndex[nextNumber]!!.lastRoundSpoken
        numbersIndex[nextNumber]!!.lastRoundSpoken = round
        numbersIndex[nextNumber]!!.timesSpoken += 1
        lastSpoken = nextNumber
        ++round
        return nextNumber
    }
}

data class NumbersIndex(var timesSpoken: Int = 0, var lastRoundSpoken: Int = 0, var prevRoundSpoken: Int = 0) {
    override fun toString() =
        "[timesSpoken: $timesSpoken, lastRoundSpoken: $lastRoundSpoken, prevRoundSpoken:: $prevRoundSpoken]"
}