package mpdev.springboot.aoc2020.solutions.day06

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureNanoTime

@Component
class Day06: PuzzleSolver() {

    final override fun setDay() {
        day = 6
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var data: MutableList<List<List<Char>>>

    override fun initSolver(): Pair<Long,String> {
        data = mutableListOf()
        val elapsed = measureNanoTime {
            processInput(inputData)
        }
        return Pair(elapsed/1000, "micro-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            result = data.sumOf { it.flatten().distinct().count() }
        }
        return PuzzlePartSolution(1, result.toString(), elapsed/1000, "micro-sec")
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            result = data.sumOf {
                it.fold(('a'..'z').toSet()) { acc, chars -> acc intersect chars.toSet() }.size
            }
        }
        return PuzzlePartSolution(2, result.toString(), elapsed/1000, "micro-sec")
    }

    fun processInput(input: List<String>) {
        var inputStr = mutableListOf<List<Char>>()
        input.forEach { line ->
            if (line.isNotEmpty())
                inputStr.add(line.toList())
            else  {
                data.add(inputStr)
                inputStr = mutableListOf()
            }
        }
        data.add(inputStr)
        log.info("forms size: {}", data.size)
    }
}