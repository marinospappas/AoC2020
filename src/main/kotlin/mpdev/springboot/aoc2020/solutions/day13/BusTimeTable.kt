package mpdev.springboot.aoc2020.solutions.day13

class BusTimeTable(input: String) {

    var timetableData: Map<Int, MutableList<Int>>
    var busSchedule: List<Pair<Int,Int>>

    init {
        timetableData = input.split(",").filter { it != "x" }
            .map { it.toInt() }.associateWith { mutableListOf() }
        busSchedule = input.split(",").let { item -> item.indices.map { i -> Pair(i, item[i]) } }
            .filter { it.second.all { c -> c.isDigit() } }
            .map { it.first to it.second.toInt() }
    }

    fun fillTimetable(untilTime: Int) {
        timetableData.forEach { (id, list) ->
            var time = -id
            while (time < untilTime) {
                time += id
                list.add(time)
            }
        }
    }

    var recursionLevel = 0

    /**
     * recursively call busesSequence for indx-1 and find the first element that satisfies the below equations:
     * id1 * n1 = t
     * id2 * n2 = t + k
     *
     * id1, id2: the ids of the two buses
     * n1, n2: the number of the current round for each bus
     * k: the difference of the two timestamps
     */
    fun busesSequence(index: Int): Sequence<Long> {
        ++recursionLevel
        if (index == 0) {
            return generateSequence(1) { it + 1 }
        }
        val id = busSchedule[index].second
        var n_1 = 0L
        val id_1 = busSchedule[index-1].second
        val k = busSchedule[index].first - busSchedule[index-1].first
        run loop@ {
            busesSequence(index-1).forEach {
                n_1 = it
                if (((k + id_1 * n_1) % id) == 0L)
                    return@loop
            }
        }
        val n = (k + id_1 * n_1) / id
        return generateSequence(n) {
            // all ids are prime so increment the round by the product of all previous ids
            // this will maintain the previous buses sequence
            it + busSchedule.subList(0,index).fold(1L) { acc, pair -> acc * pair.second }
        }
    }

}
