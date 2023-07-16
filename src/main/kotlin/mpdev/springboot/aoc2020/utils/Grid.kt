package mpdev.springboot.aoc2020.utils

import java.awt.Point

class Grid<T>(input: List<String>, private val mapper: Map<Char,T>) {

    companion object {
        const val DEFAULT_CHAR = '.'
    }

    private var data = mutableMapOf<Point,T>()
    private var maxX: Int
    private var maxY: Int

    init {
        input.indices.forEach { y ->
                input[y].indices.forEach { x ->
                    if (mapper[input[y][x]] != null)
                        data[Point(x, y)] = mapper[input[y][x]]!!
                }

        }
        maxX = input.first().length
        maxY = input.size
    }

    fun getData() = data.toMap()
    fun getDataPoint(p: Point) = data[p]
    fun setDataPoint(p: Point, t: T) {
        data[p] = t
    }

    fun getDimensions() = Pair(maxX,maxY)
    fun countOf(item: T) = data.values.count { it == item }

    private fun data2Grid(): Array<CharArray> {
        val grid: Array<CharArray> = Array(maxY) { CharArray(maxX) { DEFAULT_CHAR } }
        data.forEach { (pos, item) -> grid[pos.y][pos.x] = map2Char(item) }
        return grid
    }

    private fun map2Char(t: T) = mapper.entries.first { e -> e.value == t }.key

    fun print() {
        val grid = data2Grid()
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