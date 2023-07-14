package mpdev.springboot.aoc2020.solutions.day10

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day10: PuzzleSolver() {

    final override fun setDay() {
        day = 10
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var data: List<Int>

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            data = inputData.map { it.toInt() }.sorted()
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            var countDiff1 = 0
            var countDiff3 = 0
            for (i in (0 .. data.size-2)) {
                when (data[i+1] - data[i]) {
                    3 -> ++countDiff3
                    1 -> ++countDiff1
                }
            }
            result = (countDiff1+1) * (countDiff3+1)
        }
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            val jolt = JoltAdapters(inputData)
            result = jolt.getAllPaths(0,jolt.data.max()+3).size
        }
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }

}