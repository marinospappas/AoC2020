package mpdev.springboot.aoc2020.solutions.day03

import mpdev.springboot.aoc2020.utils.Grid
import java.awt.Point
import mpdev.springboot.aoc2020.utils.plus

class Slope(val input: List<String>) {

    var grid = Grid(input, SlopePoint.mapper)

    val maxX = grid.getDimensions().first
    val maxY = grid.getDimensions().second

    private var start = Point(0,0)

    fun countOf(item: SlopePoint) = grid.countOf(item)

    fun traverse(step: Point): Int {
        var current = start
        var encountered = 0
        while (current.y < maxY) {
            current += step
            if (grid.getData().getExtended(current) == SlopePoint.TREE)
                ++encountered
        }
        return encountered
    }

    fun printSlope() {
        grid.print()
    }

    private fun Map<Point,SlopePoint>.getExtended(p: Point)
        = this[Point(if (p.x < maxX) p.x else p.x % maxX, p.y)]
}

enum class SlopePoint(val value: Char) {
    TREE('#'),
    ENCOUNTERED('X');

    companion object {
        val mapper: Map<Char,SlopePoint> = values().associateBy { it.value }
    }
}