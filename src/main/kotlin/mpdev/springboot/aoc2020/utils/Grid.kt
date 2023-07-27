package mpdev.springboot.aoc2020.utils

import java.awt.Point

open class Grid<T>(input: List<String> = emptyList(), private val mapper: Map<Char,T>) {

    private var data = mutableMapOf<Point,T>()
    private var maxX: Int = 0
    private var maxY: Int = 0
    private var minX: Int = 0
    private var minY: Int = 0

    init {
        if (input.isNotEmpty()) {
            processInput(input)
            maxX = data.keys.maxOf { it.x }
            maxY = data.keys.maxOf { it.y }
        }
    }

    constructor(gridData: Map<Point,T>,  mapper: Map<Char,T>): this(mapper = mapper) {
        data = gridData.toMutableMap()
        maxX = data.keys.maxOf { it.x }
        maxY = data.keys.maxOf { it.y }
    }

    open fun getData() = data.toMap()
    open fun getDataPoint(p: Point) = data[p]
    open fun setDataPoint(p: Point, t: T) {
        data[p] = t
    }

    open fun getDimensions() = Pair(maxX-minX+1, maxY-minY+1)
    open fun countOf(item: T) = data.values.count { it == item }

    companion object {
        const val DEFAULT_CHAR = '.'
    }

    private fun processInput(input: List<String>) {
        input.indices.forEach { y ->
            input[y].indices.forEach { x ->
                if (mapper[input[y][x]] != null)
                    data[Point(x, y)] = mapper[input[y][x]]!!
            }
        }
    }

    private fun data2Grid(): Array<CharArray> {
        val grid: Array<CharArray> = Array(maxY+1) { CharArray(maxX+1) { DEFAULT_CHAR } }
        data.forEach { (pos, item) -> grid[pos.y][pos.x] = map2Char(item) }
        return grid
    }

    private fun map2Char(t: T) = mapper.entries.first { e -> e.value == t }.key

    open fun print() {
        printGrid(data2Grid())
    }

    private fun printGrid(grid: Array<CharArray>) {
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