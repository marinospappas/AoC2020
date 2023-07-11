package mpdev.springboot.aoc2020.solutions.day03

import mpdev.springboot.aoc2020.utils.AocException
import java.awt.Point
import mpdev.springboot.aoc2020.utils.plus

class Slope(val input: List<String>) {

    private var data: MutableMap<Point, SlopePoint> = mutableMapOf()

    var maxX = 0
    var maxY = 0
    private var start = Point(0,0)

    init {
        input.indices.forEach { y ->
            input[y].indices.forEach { x ->
                if (input[y][x] != ' ') {
                    data[Point(x, y)] = when (input[y][x]) {
                        SlopePoint.EMPTY.value -> SlopePoint.EMPTY
                        SlopePoint.TREE.value -> SlopePoint.TREE
                        else -> throw AocException("invalid input line ${input[y]}")
                    }
                }
            }
        }
        maxX = input.first().length
        maxY = input.size
    }

    fun countOf(item: SlopePoint) = data.values.count { it == item }

    fun traverse(step: Point): Int {
        var current = start
        var encountered = 0
        while (current.y < maxY) {
            current += step
            if (data.getExtended(current) == SlopePoint.TREE)
                ++encountered
        }
        return encountered
    }

    private fun slope2Grid(maze: Map<Point, SlopePoint>): Array<CharArray> {
        val grid: Array<CharArray> = Array(maxY) { CharArray(maxX) { ' ' } }
        maze.forEach { (pos, item) -> grid[pos.y][pos.x] = item.value }
        return grid
    }

    fun printSlope() {
        val grid = slope2Grid(data)
        for (i in grid.indices) {
            print("${String.format("%2d",i)} ")
            for (j in grid.first().indices)
                print(grid[i][j])
            println()
        }
        print("   ")
        for (i in grid.first().indices)
            print(if (i%10 == 0) i/10 else " ")
        println()
        print("   ")
        for (i in grid.first().indices)
            print(i%10)
        println()
    }

    private fun Map<Point,SlopePoint>.getExtended(p: Point)
        = this[Point(if (p.x < maxX) p.x else p.x % maxX, p.y)]
}

enum class SlopePoint(val value: Char) {
    EMPTY('.'),
    TREE('#'),
    ENCOUNTERED('X')
}