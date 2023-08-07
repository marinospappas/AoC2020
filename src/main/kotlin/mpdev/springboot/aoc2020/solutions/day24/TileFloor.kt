package mpdev.springboot.aoc2020.solutions.day24

import mpdev.springboot.aoc2020.utils.HexGrid
import java.awt.Point
import mpdev.springboot.aoc2020.utils.plus

class TileFloor(input: List<String>) {

    var tiles: HexGrid<Tile> = HexGrid(mapper = Tile.mapper)
    var instructions: List<List<HexGrid.Directions>>
    private val referenceTile = Point(0,0)

    init {
        instructions = processInput(input)
    }

    fun flipTile(stepList: List<HexGrid.Directions>) {
        val destinationTile = stepList.fold(referenceTile) { acc, directions -> acc + directions.step }
        tiles.setDataPoint(destinationTile, if (tiles.getDataPoint(destinationTile) != Tile.BLACK) Tile.BLACK else Tile.WHITE)
    }

    fun flipAllTiles() {
        instructions.forEach { stepList -> flipTile(stepList) }
    }

    fun flipTilesDaily() {
        tiles.extendGrid()
        val thisFloor = tiles.getDataPoints()
        tiles.getAllPointsInGrid().forEach { point ->
            val blackCount = tiles.getAdjacent(point).count { thisFloor[it] == Tile.BLACK }
            if (thisFloor[point] == Tile.BLACK) {
                if (blackCount == 0 || blackCount > 2)
                    tiles.setDataPoint(point, Tile.WHITE)
            }
            else {
                if (blackCount == 2)
                    tiles.setDataPoint(point, Tile.BLACK)
            }
        }
        tiles.updateDimensions()
    }

    fun print() {
        tiles.updateDimensions()
        tiles.print()
    }

    ////////////////////////////////////////////////////////////////

    private fun processInput(input: List<String>): List<List<HexGrid.Directions>> {
        val instructions = mutableListOf<List<HexGrid.Directions>>()
        input.forEach { line -> instructions.add(processLine(line)) }
        return instructions.toList()
    }

    private fun processLine(line: String): List<HexGrid.Directions> {
        val instructionList = mutableListOf<HexGrid.Directions>()
        // e, se, sw, w, nw, and ne
        // seswneswswsenwwnwse
        var input = line
        while (input.isNotEmpty()) {
            when {
                input.startsWith(HexGrid.Directions.E.strValue) -> {
                    instructionList.add(HexGrid.Directions.E)
                    input = input.substring(1)
                }
                input.startsWith(HexGrid.Directions.SE.strValue) -> {
                    instructionList.add(HexGrid.Directions.SE)
                    input = input.substring(2)
                }
                input.startsWith(HexGrid.Directions.SW.strValue) -> {
                    instructionList.add(HexGrid.Directions.SW)
                    input = input.substring(2)
                }
                input.startsWith(HexGrid.Directions.W.strValue) -> {
                    instructionList.add(HexGrid.Directions.W)
                    input = input.substring(1)
                }
                input.startsWith(HexGrid.Directions.NW.strValue) -> {
                    instructionList.add(HexGrid.Directions.NW)
                    input = input.substring(2)
                }
                input.startsWith(HexGrid.Directions.NE.strValue) -> {
                    instructionList.add(HexGrid.Directions.NE)
                    input = input.substring(2)
                }
            }
        }
        return instructionList.toList()
    }
}

enum class Tile(val value: Char) {
    BLACK('#'),
    WHITE('.'),
    REF_BLACK('B'),
    REF_WHITE('W');
    companion object {
        val mapper: Map<Char,Tile> = values().associateBy { it.value }
    }
}