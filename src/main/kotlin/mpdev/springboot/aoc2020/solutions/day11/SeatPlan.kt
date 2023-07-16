package mpdev.springboot.aoc2020.solutions.day11

import mpdev.springboot.aoc2020.utils.Grid
import mpdev.springboot.aoc2020.utils.plus
import mpdev.springboot.aoc2020.utils.times
import java.awt.Point

class SeatPlan(val input: List<String>) {

    var grid = Grid(input, Seat.mapper)

    val maxX = grid.getDimensions().first
    val maxY = grid.getDimensions().second

    private val adjacentSeats: Array<Point> =
        listOf(Point(-1,0), Point(-1,-1), Point(0,-1), Point(1,-1),
        Point(1,0), Point(1,1), Point(0,1), Point(-1,1)).toTypedArray()

    fun countOf(item: Seat) = grid.countOf(item)

    fun getAdjacentOccupiedPart1(seatingPlan: Map<Point,Seat>, position: Point) =
        adjacentSeats.count { seatingPlan[position+it] == Seat.OCCUPIED }

    fun getAdjacentOccupiedPart2(seatingPlan: Map<Point,Seat>, position: Point): Int {
        val occupied = Array(8) { -1 }
        var n = 1
        while (occupied.any { it == -1 }) {
            for (i in adjacentSeats.indices) {
                if (occupied[i] >= 0)
                    continue
                when (seatingPlan[position + adjacentSeats[i] * n]) {
                    null, Seat.EMPTY -> occupied[i] = 0
                    Seat.OCCUPIED -> occupied[i] = 1
                    Seat.FLOOR -> {}
                }
            }
            ++n
        }
        return occupied.sum()
    }

    fun adjustSeating(findOccupied: (Map<Point,Seat>, Point) -> Int, minOccupied: Int) {
        val currentSeating = grid.getData().toMap()
        currentSeating.forEach { seat ->
            when (findOccupied(currentSeating, seat.key)) {
                0 -> if (grid.getDataPoint(seat.key) == Seat.EMPTY)
                    grid.setDataPoint(seat.key, Seat.OCCUPIED)
                in (minOccupied .. 8) ->
                    if (grid.getDataPoint(seat.key) == Seat.OCCUPIED)
                        grid.setDataPoint(seat.key, Seat.EMPTY)
            }
        }
    }

    fun printSeatPlan() {
        grid.print()
    }
}

enum class Seat(val value: Char) {
    FLOOR('.'),
    EMPTY('L'),
    OCCUPIED('#');

    companion object {
        val mapper: Map<Char,Seat> = values().associateBy { it.value }
    }
}