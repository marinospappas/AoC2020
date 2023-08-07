package mpdev.springboot.aoc2020.solutions.day20

import mpdev.springboot.aoc2020.utils.AocException
import mpdev.springboot.aoc2020.utils.Grid
import mpdev.springboot.aoc2020.solutions.day20.Perimeter.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Point
import kotlin.math.sqrt

class JigsawSolver(input: List<String>) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    var tiles: Map<Int, Grid<TilePixel>>
    var transformedTiles: Map<TransformedTile, Grid<TilePixel>>
    // keep all the perimeters of all the transformed tiles here for performance - the pixels are encoded as integers
    var transformedTilesPerimeter: Map<TransformedTile, List<Int>>
    var tilesNoBorders: Map<Int, Grid<TilePixel>>
    var jigsawSize = 0
    var sideSize = 0
    var tileSize = 0
    val shapeToMatch = Grid(listOf(
        "                  # ",
        "#    ##    ##    ###",
        " #  #  #  #  #  #   "
    ), TilePixel.mapper)

    init {
        tiles =  processInput(input)
        jigsawSize = tiles.size
        sideSize = sqrt(tiles.size.toDouble()).toInt()
        tileSize = tiles.values.first().getDimensions().first    // assuming that tiles are square
        val (transformedTiles, transformedTilesPerimeter) = getAllTransformedTiles()
        this.transformedTiles = transformedTiles
        this.transformedTilesPerimeter = transformedTilesPerimeter
        tilesNoBorders = removeBorders()
    }

    var solutions = listOf<List<TransformedTile>>()

    fun getSolution() = solutions.firstOrNull { it.size == jigsawSize } ?: emptyList()

    ////// part 1

    fun getSolutionCornerIds() =
        listOf(getSolution()[0].tileId, getSolution()[sideSize-1].tileId, getSolution()[sideSize * (sideSize-1)].tileId,
            getSolution()[sideSize*sideSize - 1].tileId)

    fun solve() {
        log.info("solving jigsaw")
        var currentList: List<List<TransformedTile>>
        val newList =  mutableListOf<List<TransformedTile>>(listOf())
        repeat(jigsawSize) {
            // for each potential solution produce a new set of potential solutions by identifying all tiles that fit next
            currentList = newList.toList()
            newList.clear()
            currentList.forEach { sol ->
                newList.addAll(findTilesThatFitJigSaw(sol))
            }
            if ((it+1) % sideSize == 0 || it < sideSize) {
                log.info("round {} completed, solutions list size {}", it+1, newList.size)
                //newList.forEach { tile -> println(tile) }
            }
            if (it+1 == sideSize) {
                // cut down the list by looking for pairs of entries that have the two top corners symmetric
                newList.removeAll(cleanupSymmetricDoubleEntries(newList))
                log.info("after round {} the list is cleaned up from symmetric double solutions - new list size {}", it+1, newList.size)
                //newList.forEach { tile -> println(tile) }
            }
        }
        solutions = newList.toList()
    }

    private fun cleanupSymmetricDoubleEntries(solutions: List<List<TransformedTile>>): List<List<TransformedTile>> {
        val listToRemove = mutableListOf<List<TransformedTile>>()
        solutions.forEach { sol ->
            if (listToRemove.contains(sol))
                return@forEach
            listToRemove.addAll(solutions.filter { e -> e.first().tileId == sol.last().tileId && e.last().tileId == sol.first().tileId })
        }
        return listToRemove
    }

    private fun findTilesThatFitJigSaw(currSolution: List<TransformedTile>): List<List<TransformedTile>> {
        val jigsaw = currSolution.toMutableList()
        val remainingTiles = tiles.keys - jigsaw.map { it.tileId }.toSet()
        val result = mutableListOf<List<TransformedTile>>()
        // try each remaining tile in each orientation to see which one fit and add them to the list
        remainingTiles.forEach { tileId ->
            Transformation.values().forEach { transformation ->
                val transformedTile = TransformedTile(tileId, transformation)
                if (tileFits(transformedTile, jigsaw)) {
                    result.add(jigsaw.toMutableList().also { it.add(transformedTile) })
                }
            }
        }
        return if (result.any { it.size > jigsaw.size }) result else emptyList()
    }

    private fun tileFits(tile: TransformedTile, jigsaw: List<TransformedTile>): Boolean {
        if (jigsaw.size >= sideSize)
            if (!tileFitsWithTop(tile, jigsaw))
                return false
        if ((jigsaw.lastIndex + 1) % sideSize != 0)
            if (!tileFitsWithLeft(tile, jigsaw))
                return false
        return true
    }

    private fun tileFitsWithTop(tile: TransformedTile, jigsaw: List<TransformedTile>): Boolean {
        val newTileIndex = jigsaw.lastIndex + 1
        val tileAboveIndex = newTileIndex - sideSize
        return transformedTilesPerimeter[tile]?.get(TOP.ordinal) ==
                transformedTilesPerimeter[TransformedTile(jigsaw[tileAboveIndex].tileId, jigsaw[tileAboveIndex].transformedState)]?.get(BOTTOM.ordinal)
    }

    private fun tileFitsWithLeft(tile: TransformedTile, jigsaw: List<TransformedTile>): Boolean {
        val newTileIndex = jigsaw.lastIndex + 1
        val tileLeftIndex = newTileIndex - 1
        return transformedTilesPerimeter[tile]?.get(LEFT.ordinal) ==
                transformedTilesPerimeter[TransformedTile(jigsaw[tileLeftIndex].tileId, jigsaw[tileLeftIndex].transformedState)]?.get(RIGHT.ordinal)
    }

    //////// part 2

    fun constructJigsaw(): Grid<TilePixel> {
        val jigsawData = mutableMapOf<Point, TilePixel>()
        val solution = getSolution()
        solution.indices.forEach { index ->
            val transformedTile = solution[index]
            val offsX = (index % sideSize) * (tileSize - 2)
            val offsY = index / sideSize * (tileSize - 2)
            val grid = Transformation.apply(
                tilesNoBorders[transformedTile.tileId]!!, transformedTile.transformedState, tileSize - 2, tileSize - 2
            )
            grid.getDataPoints().forEach { jigsawData[Point(it.key.x + offsX, it.key.y + offsY)] = it.value }
        }
        return Grid(jigsawData, TilePixel.mapper)
    }

    fun matchShape(picture: Grid<TilePixel>): Grid<TilePixel>? {
        Transformation.values().forEach { transformation ->
            val transformedPicture = Transformation.apply(picture, transformation, picture.getDimensions().first, picture.getDimensions().second)
            if (findShapeInPicture(transformedPicture, shapeToMatch))
                return transformedPicture
        }
        return null
    }

    private fun findShapeInPicture(picture: Grid<TilePixel>, shape: Grid<TilePixel>): Boolean {
        var found = false
        val picSize = picture.getDimensions().first
        val shapeLength = shape.getDimensions().first
        picture.getDataPoints()
            .filter { it.key.y > 1 && it.key.y < picSize - 2 && it.key.x < picSize - shapeLength }
            .forEach { picEntry ->
                val xOffs = picEntry.key.x
                val yOffs = picEntry.key.y - 1
                val dataToMatch = shape.getDataPoints().keys.map { Point(it.x + xOffs, it.y + yOffs) }.toSet()
            if (dataToMatch.none { picture.getDataPoint(it) == null }) {
                    dataToMatch.forEach { point -> picture.setDataPoint(point, TilePixel.MONSTER) }
                    found = true
                }
            }
        return found
    }

    /////// initialisation

    private fun removeBorders(): Map<Int, Grid<TilePixel>> {
        val tilesWithoutBorders = mutableMapOf<Int, Grid<TilePixel>>()
        tiles.forEach { tile ->
            tilesWithoutBorders[tile.key] =
                Grid(tile.value.getDataPoints().filter { it.key.x != 0 && it.key.y != 0 && it.key.x != tileSize-1 && it.key.y != tileSize-1 }
                    .mapKeys { Point(it.key.x-1, it.key.y-1) }, TilePixel.mapper)
        }
        return tilesWithoutBorders
    }

    private fun getAllTransformedTiles(): Pair< Map<TransformedTile, Grid<TilePixel>>, Map<TransformedTile, List<Int>> > {
        val grids = mutableMapOf<TransformedTile, Grid<TilePixel>>()
        val perimeters = mutableMapOf<TransformedTile, List<Int>>()
        tiles.forEach { (tileId, grid) ->
            Transformation.values().forEach { transformation ->
                val transformedGrid = Transformation.apply(grid, transformation, tileSize, tileSize)
                grids[TransformedTile(tileId, transformation)] = transformedGrid
                val top = transformedGrid.mapRowToInt(0)
                val right = transformedGrid.mapColToInt(tileSize - 1)
                val bottom = transformedGrid.mapRowToInt(tileSize - 1)
                val left = transformedGrid.mapColToInt(0)
                perimeters[TransformedTile(tileId, transformation)] = listOf(top, right, bottom, left)
            }
        }
        return Pair(grids, perimeters)
    }

    /////////////////////////////////////////////

    private fun processInput(input: List<String>): Map<Int, Grid<TilePixel>> {
        val result = mutableMapOf<Int, Grid<TilePixel>>()
        var tileId = 0
        val tileData = mutableListOf<String>()
        input.forEach { line ->
            try {
                when {
                    // Tile 2311:
                    line.startsWith("Tile ") -> {
                        val (tileIdStr) = Regex("""Tile (\d+):""").find(line)!!.destructured
                        tileId = tileIdStr.toInt()
                    }
                    line.isEmpty() -> {
                        result[tileId] = Grid(tileData, TilePixel.mapper)
                        tileData.clear()
                    }
                    else -> tileData.add(line)
                }
            }
            catch (e: Exception) {
                throw AocException("invalid input line [$line]")
            }
        }
        result[tileId] = Grid(tileData, TilePixel.mapper)
        return result
    }

    fun printTiles() {
        tiles.forEach { tile -> println("Tile: ${tile.key}"); tile.value.print() }
    }
}
