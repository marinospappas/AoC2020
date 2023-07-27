package mpdev.springboot.aoc2020.solutions.day19

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import mpdev.springboot.aoc2020.solutions.day19.MessageValidator.Companion.MATCHED
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day19: PuzzleSolver() {


    final override fun setDay() {
        day = 19
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var msgValidator: MessageValidator

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            msgValidator = MessageValidator(inputData)
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            result = msgValidator.messages.count { msgValidator.match(it, 0) == MATCHED }
        }
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            /*
            initial setting:
              0: 8 11
              8: 42
              11: 42 31
            change of rules to include loops (repetition of rule)
              8: 42 8 | 42            -> 42+ (one or more times)
              11: 42 11 31 | 42 31    -> 42+ 31+ (42 once or more followed by 31 once or more - both matched the same number of times)
            so rule 0 is now equivalent to
              0: 42+ 42+ 31+          -> 42 twice or more followed by 31 once or more
                                        (42 must be matched at least once more than the times 31 is matched)
             */
            result = msgValidator.messages.count { msgValidator.match2(it, 42, 31) }
        }
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }
}