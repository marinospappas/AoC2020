package mpdev.springboot.aoc2020.solutions.day17

import mpdev.springboot.aoc2020.utils.*

class ConwayCubes(val input: List<String>, part1Or2: Int = 1) {

    var grid: GridND<Cube>

    init {
        grid = if (part1Or2 == 1)
            GridND(input, Cube.mapper, 3)
        else
            GridND(input, Cube.mapper, 4)
    }

    fun countOf(item: Cube) = grid.countOf(item)

    fun runCycle() {
        grid.expand(1)
        val prevGrid = grid.getData().toMap()
        grid.getAllPointsInGrid().forEach { point ->
            val adjacentActive = point.adjacent().count { prevGrid[it] == Cube.ACTIVE }
            if (prevGrid[point] == Cube.ACTIVE) {
                if (adjacentActive < 2 || adjacentActive > 3)
                    grid.setDataPoint(point, Cube.INACTIVE)
            }
            else {
                if (adjacentActive == 3)
                    grid.setDataPoint(point, Cube.ACTIVE)
            }
        }
    }

    fun printCubes() {
        grid.print()
    }
}

enum class Cube(val value: Char) {
    INACTIVE('.'),
    ACTIVE('#');

    companion object {
        val mapper: Map<Char,Cube> = values().associateBy { it.value }
    }
}