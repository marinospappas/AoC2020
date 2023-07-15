package mpdev.springboot.aoc2020.solutions.day10

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureNanoTime

@Component
class Day10: PuzzleSolver() {

    final override fun setDay() {
        day = 10
    }

    init {
        setDay()
    }

    var result = 0L
    lateinit var data: List<Int>

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureNanoTime {
            data = inputData.map { it.toInt() }.sorted()
        }
        return Pair(elapsed/1000, "micro-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            var countDiff1 = 0L
            var countDiff3 = 0L
            for (i in (0 .. data.size-2)) {
                when (data[i+1] - data[i]) {
                    3 -> ++countDiff3
                    1 -> ++countDiff1
                }
            }
            result = (countDiff1+1) * (countDiff3+1)
        }
        return PuzzlePartSolution(1, result.toString(), elapsed/1000, "micro-sec")
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            val adapters = JoltAdapters(inputData)
            result = adapters.getAllCombinations()
        }
        return PuzzlePartSolution(2, result.toString(), elapsed/1000, "micro-sec")
    }

}