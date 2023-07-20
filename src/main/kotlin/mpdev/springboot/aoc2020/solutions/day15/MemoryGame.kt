package mpdev.springboot.aoc2020.solutions.day15

class MemoryGame(input: List<String>) {

    companion object {
        const val MAX_ROUNDS = 30_000_000
    }

    val startSequence = input[0].split(",").map { it.toInt() }
    private val startSequenceSize = startSequence.size

    private var round = 0
    private lateinit var lastSpokenInfo: NumbersInfo

    // keep a cache of the info for ALL potential numbers in Array for maximum performance
    // a Map should be used instead in case of memory constraints in which case only info for numbers spoken would be saved
    val infoCache = Array(MAX_ROUNDS) { NumbersInfo() }
    // information about 0 (most frequent number) is cached here for even better performance
    var numberZeroInfo = NumbersInfo()

    private var nextNumber = 0

    fun resetGame() {
        infoCache.forEach { it.timesSpoken = 0; it.lastRoundSpoken = 0; it.prevRoundSpoken = 0 }
        round = 0
    }

    fun getLastNumber() = nextNumber

    fun playRound() {
        nextNumber = if (round < startSequenceSize) {
            startSequence[round]
        }
        else {
            if (lastSpokenInfo.timesSpoken == 1)
                0
            else
                lastSpokenInfo.lastRoundSpoken - lastSpokenInfo.prevRoundSpoken
        }
        updateNumberInfo(nextNumber)
        ++round
    }

    private fun updateNumberInfo(number: Int) {
        val numberInfo = if (number == 0)
            numberZeroInfo
        else
            infoCache[number]
        numberInfo.prevRoundSpoken = numberInfo.lastRoundSpoken
        numberInfo.lastRoundSpoken = round
        numberInfo.timesSpoken += 1
        lastSpokenInfo = numberInfo
    }
}

data class NumbersInfo(var timesSpoken: Int = 0, var lastRoundSpoken: Int = 0, var prevRoundSpoken: Int = 0) {
    override fun toString() =
        "[timesSpoken: $timesSpoken, lastRoundSpoken: $lastRoundSpoken, prevRoundSpoken:: $prevRoundSpoken]"
}