package mpdev.springboot.aoc2020.solutions.day15

class MemoryGame(input: List<String>) {

    val startSequence = input[0].split(",").map { it.toInt() }
    var round = 0
    val startSequenceSize = startSequence.size
    val numbersIndex = startSequence.associateWith { NumbersIndex() }.toMutableMap()
    var lastSpoken = -1

    fun playRound(): Int {
        var nextNumber: Int
        if (round < startSequenceSize) {
            nextNumber = startSequence[round]
        }
        else {
            nextNumber = lastSpoken
            if (numbersIndex[nextNumber]!!.timesSpoken == 1)
                nextNumber = 0
            else
                nextNumber = numbersIndex[nextNumber]!!.lastRoundSpoken - numbersIndex[nextNumber]!!.prevRoundSpoken
        }
        var numberIndex: NumbersIndex?
        if (numbersIndex[nextNumber].also { numberIndex = it } == null)
            numberIndex = NumbersIndex()
        numberIndex!!.prevRoundSpoken = numberIndex!!.lastRoundSpoken
        numberIndex!!.lastRoundSpoken = round
        numberIndex!!.timesSpoken += 1
        numbersIndex[nextNumber] = numberIndex!!
        lastSpoken = nextNumber
        ++round
        return nextNumber
    }
}

data class NumbersIndex(var timesSpoken: Int = 0, var lastRoundSpoken: Int = 0, var prevRoundSpoken: Int = 0) {
    override fun toString() =
        "[timesSpoken: $timesSpoken, lastRoundSpoken: $lastRoundSpoken, prevRoundSpoken:: $prevRoundSpoken]"
}