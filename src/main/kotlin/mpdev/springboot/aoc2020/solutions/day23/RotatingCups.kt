package mpdev.springboot.aoc2020.solutions.day23

class RotatingCups(input: List<String>, part1or2: Int = 1) {

    // the cups are kept in a linked list so that the order can be maintained or changed easily
    private var cups: Cup
    // in addition, all cups are stored in an array with their id as index, so that each cup can be accessed fast by its id
    var cupsMap: Array<Cup>
    var current: Cup
    var size = 0
    var maxCupId: Int

    init {
        cupsMap = Array(if (part1or2 == 1) input[0].length + 1 else 1_000_001) { Cup(0) }
        val chars = input[0].toCharArray()
        current = Cup(chars[0] - '0').also { cupsMap[chars[0] - '0'] = it }
        cups = current
        ++size
        (1 .. chars.lastIndex).forEach { addCup(chars[it] - '0') }
        maxCupId = chars.maxOf { it - '0' }
        if (part1or2 == 2) {
            (maxCupId+1 .. 1_000_000).forEach { addCup(it)  }
            maxCupId = 1_000_000
        }
        current.next = cups
        current = cups
    }

    private fun addCup(cupId: Int) {
        current.next = Cup(cupId).also { cup -> cupsMap[cupId] = cup }
        current = current.next
        ++size
    }

    fun playRound() {
        val firstCupToMove = current.next
        val lastCupToMove = firstCupToMove.next.next
        val cupIdsToMove = intArrayOf(firstCupToMove.id, firstCupToMove.next.id, lastCupToMove.id)
        current.next = lastCupToMove.next
        var destinationId = current.id
        do {
            --destinationId
            if (destinationId < 1)
                destinationId = maxCupId
        } while (cupIdsToMove.contains(destinationId))
        val destination = cupsMap[destinationId]
        val destinationNext = destination.next
        destination.next = firstCupToMove
        lastCupToMove.next = destinationNext
        current = current.next
    }

    fun getCup(id: Int) = cupsMap[id]

    fun cupsToList(startId: Int) = cupsToList(startId, Int.MAX_VALUE)

    fun cupsToList(startId: Int, limit: Int): List<Int> {
        var cup: Cup = cupsMap[startId]
        val cupsList = mutableListOf(cup.id)
        var count = 0
        while (cup.next.id != startId && ++count < limit) {
            cup = cup.next
            cupsList.add(cup.id)
        }
        return cupsList
    }

    fun toString(limit: Int): String {
        val thisFirstCup = current
        var currentCup = current
        var count = 0
        var s = "(${currentCup.id}), "
        while (currentCup.next != thisFirstCup && ++count < limit) {
            currentCup = currentCup.next
            s += "${currentCup.id}, "
        }
        return s.removeSuffix(", ")
    }

    override fun toString() = toString(Int.MAX_VALUE)
}

data class Cup(val id: Int) {
    lateinit var next: Cup
    override fun toString() = "id = $id, next id = ${next.id}"
}