package mpdev.springboot.aoc2020.solutions.day03

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import java.awt.Point
import kotlin.system.measureNanoTime

@Component
class Day03: PuzzleSolver() {

    final override fun setDay() {
        day = 3
    }

    init {
        setDay()
    }

    var result = 0L
    lateinit var slope: Slope

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureNanoTime {
            slope = Slope(inputData)
        }
        return Pair(elapsed/1000, "micro-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            result = slope.traverse(Point(3,1)).toLong()
        }
        return PuzzlePartSolution(1, result.toString(), elapsed/1000, "micro-sec")
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            val res = mutableListOf<Long>()
            listOf(Point(1,1), Point(3,1), Point(5,1), Point(7,1), Point(1,2))
                .forEach { res.add(slope.traverse(it).toLong()) }
            result = res.fold(1L) { product, it -> product * it }
        }
        return PuzzlePartSolution(2, result.toString(), elapsed/1000, "micro-sec")
    }

}