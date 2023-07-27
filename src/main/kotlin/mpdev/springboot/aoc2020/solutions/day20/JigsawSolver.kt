package mpdev.springboot.aoc2020.solutions.day20

import mpdev.springboot.aoc2020.utils.AocException
import mpdev.springboot.aoc2020.utils.Grid
import mpdev.springboot.aoc2020.solutions.day20.Perimeter.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.math.sqrt

class JigsawSolver(input: List<String>) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    var tiles: Map<Int, Grid<TilePixel>>
    var transformedTiles: Map<TransformedTile, Grid<TilePixel>>
    var transformedTilesPerimeter: Map<TransformedTile, List<Set<Int>>>
    var jigsawSize = 0
    var sideSize = 0
    var tileSize = 0

    init {
        tiles =  processInput(input)
        jigsawSize = tiles.size
        sideSize = sqrt(tiles.size.toDouble()).toInt()
        tileSize = tiles.values.first().getDimensions().first    // assuming that tiles are square
        Transformation.maxX = tileSize -1
        Transformation.maxY = tileSize -1
        val (transformedTiles, transformedTilesPerimeter) = getAllTransformedTiles()
        this.transformedTiles = transformedTiles
        this.transformedTilesPerimeter = transformedTilesPerimeter
    }

    var solutions = listOf<List<TransformedTile>>()

    fun getSolution() = solutions.firstOrNull { it.size == jigsawSize } ?: emptyList()
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
            if (it % sideSize == 0)
                log.info("round {} completed", it)
        }
        log.info("round {} completed", jigsawSize)
        solutions = newList.toList()
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
        val newTileTopData = transformedTilesPerimeter[tile]?.get(TOP.ordinal)
            ?: throw AocException("could not get tile $tile")
        val tileAboveBottomData = transformedTilesPerimeter[TransformedTile(jigsaw[tileAboveIndex].tileId, jigsaw[tileAboveIndex].transformedState)]?.get(BOTTOM.ordinal)
            ?: throw AocException("could not get tile ${TransformedTile(jigsaw[tileAboveIndex].tileId, jigsaw[tileAboveIndex].transformedState)}")
        return newTileTopData == tileAboveBottomData
    }

    private fun tileFitsWithLeft(tile: TransformedTile, jigsaw: List<TransformedTile>): Boolean {
        val newTileIndex = jigsaw.lastIndex + 1
        val tileLeftIndex = newTileIndex - 1
        val newTileLeftData = transformedTilesPerimeter[tile]?.get(LEFT.ordinal)
            ?: throw AocException("could not get tile $tile")
        val tileLeftRightData = transformedTilesPerimeter[TransformedTile(jigsaw[tileLeftIndex].tileId, jigsaw[tileLeftIndex].transformedState)]?.get(RIGHT.ordinal)
            ?: throw AocException("could not get tile ${TransformedTile(jigsaw[tileLeftIndex].tileId, jigsaw[tileLeftIndex].transformedState)}")
        return newTileLeftData == tileLeftRightData
    }

    /////////////////////////////////////////////

    private fun getAllTransformedTiles(): Pair< Map<TransformedTile, Grid<TilePixel>>, Map<TransformedTile, List<Set<Int>>> > {
        val grids = mutableMapOf<TransformedTile, Grid<TilePixel>>()
        val perimeters = mutableMapOf<TransformedTile, List<Set<Int>>>()
        tiles.forEach { (tileId, grid) ->
            Transformation.values().forEach { transformation ->
                val transformedGrid = Transformation.apply(grid, transformation)
                grids[TransformedTile(tileId, transformation)] = transformedGrid
                val top = transformedGrid.getData().filter { entry -> entry.key.y == 0 }.map { entry -> entry.key.x }.sorted().toSet()
                val right = transformedGrid.getData().filter { entry -> entry.key.x == tileSize - 1 }.map { entry -> entry.key.y }.sorted().toSet()
                val bottom = transformedGrid.getData().filter { entry -> entry.key.y ==  tileSize - 1 }.map { entry -> entry.key.x }.sorted().toSet()
                val left = transformedGrid.getData().filter { entry -> entry.key.x == 0 }.map { entry -> entry.key.y }.sorted().toSet()
                perimeters[TransformedTile(tileId, transformation)] = listOf(top, right, bottom, left)
            }
        }
        return Pair(grids, perimeters)
    }

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
