package mpdev.springboot.aoc2020.solutions.day07

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

@Component
class Day07: PuzzleSolver() {

    final override fun setDay() {
        day = 7
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var bagRules: BagRules
    val ownBag = "shiny gold"

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            bagRules = BagRules(inputData)
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            result = bagRules.getAllParentsToRoot(BagKey(ownBag.mapFromDictionary())).size
        }
        return PuzzlePartSolution(1, result.toString(), elapsed/1000, "micro-sec")
    }

    override fun solvePart2(): PuzzlePartSolution {
        var contents: List<Pair<Int,BagKey>>
        val elapsed = measureNanoTime {
            contents = bagRules.getBagContents(BagKey(ownBag.mapFromDictionary()))
            result = contents.filter { it.second != BagKey(ownBag.mapFromDictionary()) }.sumOf { it.first }
        }
        contents.forEach { log.info("bag contents {}", it) }
        return PuzzlePartSolution(2, result.toString(), elapsed/1000, "micro-sec")
    }

}