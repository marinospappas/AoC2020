package mpdev.springboot.aoc2020.solutions.day15

import mpdev.springboot.aoc2020.utils.AocException

class MemoryGame(input: List<String>) {

    val startSequence = input[0].split(",").map { it.toInt() }
    var round = 0
    val startSequenceSize = startSequence.size
    val numbersIndex = startSequence.associateWith { NumbersIndex() }.toMutableMap()
    var lastSpoken = -1
    lateinit var lastSpokenIndex: NumbersIndex
    var number0Index = NumbersIndex()   // information about 0 (most frequent number) is cached here

    fun playRound(): Int {
        val nextNumber: Int
        if (round < startSequenceSize) {
            nextNumber = startSequence[round]
        } else {
            if (lastSpokenIndex.timesSpoken == 1)
                nextNumber = 0
            else
                nextNumber = lastSpokenIndex.lastRoundSpoken - lastSpokenIndex.prevRoundSpoken
        }
        var numberIndex = NumbersIndex()
        if (nextNumber == 0)
            numberIndex = number0Index
        else {
            if (numbersIndex[nextNumber] != null)
                numberIndex = numbersIndex[nextNumber] ?: throw AocException("unexpected error 2020-15-2")
        }
        numberIndex.prevRoundSpoken = numberIndex.lastRoundSpoken
        numberIndex.lastRoundSpoken = round
        numberIndex.timesSpoken += 1
        if (nextNumber != 0)
            numbersIndex[nextNumber] = numberIndex
        lastSpoken = nextNumber
        lastSpokenIndex = numberIndex
        ++round
        return nextNumber
    }
}

data class NumbersIndex(var timesSpoken: Int = 0, var lastRoundSpoken: Int = 0, var prevRoundSpoken: Int = 0) {
    override fun toString() =
        "[timesSpoken: $timesSpoken, lastRoundSpoken: $lastRoundSpoken, prevRoundSpoken:: $prevRoundSpoken]"
}