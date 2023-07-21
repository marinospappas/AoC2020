package mpdev.springboot.aoc2020.utils

import java.awt.Point

open class Grid<T>(input: List<String>, private val mapper: Map<Char,T>) {

    companion object {
        const val DEFAULT_CHAR = '.'
    }

    private var data = mutableMapOf<Point,T>()
    protected var maxX: Int
    protected var maxY: Int
    protected var minX: Int = 0
    protected var minY: Int = 0

    init {
        input.indices.forEach { y ->
                input[y].indices.forEach { x ->
                    if (mapper[input[y][x]] != null)
                        data[Point(x, y)] = mapper[input[y][x]]!!
                }

        }
        maxX = input.first().length - 1
        maxY = input.size - 1
    }

    open fun getData() = data.toMap()
    open fun getDataPoint(p: Point) = data[p]
    open fun setDataPoint(p: Point, t: T) {
        data[p] = t
    }

    open fun getDimensions() = Pair(maxX-minX+1, maxY-minY+1)
    open fun countOf(item: T) = data.values.count { it == item }

    private fun data2Grid(): Array<CharArray> {
        val grid: Array<CharArray> = Array(maxY+1) { CharArray(maxX+1) { DEFAULT_CHAR } }
        data.forEach { (pos, item) -> grid[pos.y][pos.x] = map2Char(item) }
        return grid
    }

    protected fun map2Char(t: T) = mapper.entries.first { e -> e.value == t }.key

    open fun print() {
        printGrid(data2Grid())
    }

    protected fun printGrid(grid: Array<CharArray>) {
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
}