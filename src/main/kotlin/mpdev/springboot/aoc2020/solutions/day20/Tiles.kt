package mpdev.springboot.aoc2020.solutions.day20

import mpdev.springboot.aoc2020.utils.AocException
import mpdev.springboot.aoc2020.utils.Grid
import java.awt.Point

enum class TilePixel(val value: Char) {
    //BLACK('.'),   black pixels not included to improve performance
    WHITE('#');

    companion object {
        val mapper: Map<Char,TilePixel> = values().associateBy { it.value }
    }
}

enum class Perimeter {
    TOP, RIGHT, BOTTOM, LEFT
}

enum class Transformation(private val value: String) {
    AS_IS(""),
    ROTATE_1("1"),
    ROTATE_2("2"),
    ROTATE_3("3"),
    FLIP_H("H"),
    FLIP_V("V"),
    ROTATE_1_FLIP_H("1H"),
    ROTATE_1_FLIP_V("1V");

    companion object {

        var maxX = 0
        var maxY = 0

        fun apply(tile: Grid<TilePixel>, transformation: Transformation): Grid<TilePixel> =
            when (transformation) {
                AS_IS -> Grid(tile.getData(), TilePixel.mapper)
                ROTATE_1, ROTATE_2, ROTATE_3 -> rotate(tile, transformation.value.toInt())
                FLIP_H, FLIP_V -> flip(tile, transformation.value)
                ROTATE_1_FLIP_H -> flip( rotate(tile, 1), FLIP_H.value )
                ROTATE_1_FLIP_V -> flip( rotate(tile, 1), FLIP_V.value )
            }

        private fun rotate(tile: Grid<TilePixel>, factor: Int): Grid<TilePixel> {
            return when (factor) {
                1 -> Grid(tile.getData().mapKeys { entry -> Point(maxX - entry.key.y, entry.key.x) }, TilePixel.mapper)
                2 -> Grid(tile.getData().mapKeys { entry -> Point(maxX - entry.key.x, maxY - entry.key.y) }, TilePixel.mapper)
                3 -> Grid(tile.getData().mapKeys { entry -> Point(entry.key.y, maxY - entry.key.x) }, TilePixel.mapper)
                else -> throw AocException("error in rotate($factor)")
            }
        }
        private fun flip(tile: Grid<TilePixel>, factor: String): Grid<TilePixel> {
            return when (factor) {
                "V" -> Grid(tile.getData().mapKeys { entry -> Point(maxX - entry.key.x, entry.key.y) }, TilePixel.mapper)
                "H" -> Grid(tile.getData().mapKeys { entry -> Point(entry.key.x, maxY - entry.key.y) }, TilePixel.mapper)
                else -> throw AocException("error in flip($factor)")
            }
        }
    }
}

data class TransformedTile(val tileId: Int, val transformedState: Transformation) {
    override fun toString() = "TransformedTile: id=$tileId transformation=${transformedState.name}"
}
