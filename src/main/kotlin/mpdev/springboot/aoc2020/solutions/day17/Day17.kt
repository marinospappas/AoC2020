package mpdev.springboot.aoc2020.solutions.day17

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day17: PuzzleSolver() {


    final override fun setDay() {
        day = 17
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var cubes: ConwayCubes

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            cubes = ConwayCubes(inputData)
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            repeat(6) { cubes.runCycle() }
            result = cubes.countOf(Cube.ACTIVE)
        }
        log.info("part 1 grid dimensions {}", cubes.grid.getDimensions().toList())
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            cubes = ConwayCubes(inputData, 2)
            repeat(6) { cubes.runCycle() }
            result = cubes.countOf(Cube.ACTIVE)
        }
        log.info("part 2 grid dimensions {}", cubes.grid.getDimensions().toList())
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }
}