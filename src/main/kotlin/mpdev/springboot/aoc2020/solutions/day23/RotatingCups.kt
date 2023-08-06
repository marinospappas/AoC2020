package mpdev.springboot.aoc2020.solutions.day23

import mpdev.springboot.aoc2020.utils.AocException

class RotatingCups(input: List<String>, part1or2: Int = 1) {

    var current: Cup
    private var cups: Cup
    var size = 0
    var maxCupId: Int
    val cupsMap = mutableMapOf<Int,Cup>()

    init {
        val chars = input[0].toCharArray()
        current = Cup(chars[0] - '0').also { cupsMap[chars[0] - '0'] = it }
        cups = current
        ++size
        (1 .. chars.lastIndex).forEach {
            val cupId = chars[it] - '0'
            current.next = Cup(cupId).also { cup -> cupsMap[cupId] = cup }
            current = current.next
            ++size
        }
        maxCupId = chars.maxOf { it - '0' }
        if (part1or2 == 2) {
            (maxCupId+1 .. 1_000_000).forEach {
                current.next = Cup(it).also { cup -> cupsMap[it] = cup }
                current = current.next
                ++size
            }
            maxCupId = 1_000_000
        }
        current.next = cups
        current = cups
    }

    fun playRound() {
        val firstCupToMove: Cup = current.next
        val lastCupToMove: Cup = firstCupToMove.next.next
        val cupIdsToMove = setOf(firstCupToMove.id, firstCupToMove.next.id, lastCupToMove.id)
        val remainingCups: Cup = lastCupToMove.next
        current.next = remainingCups
        var destinationId = current.id - 1
        if (destinationId < 1)
            destinationId = maxCupId
        while (cupIdsToMove.contains(destinationId)) {
            --destinationId
            if (destinationId < 1)
                destinationId = maxCupId
        }
        val destination = getCup(destinationId)
        val destinationNext: Cup = destination.next
        destination.next = firstCupToMove
        lastCupToMove.next = destinationNext
        current = current.next
    }

    fun getCup(id: Int) = cupsMap[id] ?: throw AocException("could not locate cup id $id")


    fun cupsToList(startId: Int): List<Int> {
        var cup: Cup = getCup(startId)
        val cupsList = mutableListOf(cup.id)
        while (cup.next.id != startId) {
            cup = cup.next
            cupsList.add(cup.id)
        }
        return cupsList
    }

    override fun toString(): String {
        val thisFirstCup = current
        var currentCup = current
        var s = "(${currentCup.id}), "
        while (currentCup.next != thisFirstCup) {
            currentCup = currentCup.next
            s += "${currentCup.id}, "
        }
        return s.removeSuffix(", ")
    }
}

data class Cup(val id: Int) {
    lateinit var next: Cup
    override fun toString() = "id = $id, next id = ${next.id}"
}