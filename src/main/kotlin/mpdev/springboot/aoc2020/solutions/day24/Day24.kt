package mpdev.springboot.aoc2020.solutions.day24

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day24: PuzzleSolver() {

    final override fun setDay() {
        day = 24
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var tileFloor: TileFloor

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            tileFloor = TileFloor(inputData)
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            tileFloor.flipAllTiles()
        }
        tileFloor.print()
        result = tileFloor.tiles.countOf(Tile.BLACK)
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            repeat(100) { tileFloor.flipTilesDaily() }
        }
        result = tileFloor.tiles.countOf(Tile.BLACK)
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }
}