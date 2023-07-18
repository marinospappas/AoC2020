package mpdev.springboot.aoc2020.solutions.day13

class BusTimeTable(input: String) {

    var data: Map<Int, MutableList<Int>>

    init {
        data = input.split(",").filter { it != "x" }
            .map { it.toInt() }.associateWith { mutableListOf() }
    }

    fun fillTimetable(untilTime: Int) {
        data.forEach { (id, list) ->
            var time = -id
            while (time < untilTime) {
                time += id
                list.add(time)
            }
        }
    }
}