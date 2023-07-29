package mpdev.springboot.aoc2020.solutions.day21

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day21: PuzzleSolver() {


    final override fun setDay() {
        day = 21
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var foodStore: FoodStore

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            foodStore = FoodStore(inputData)
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            foodStore.findAllergensFromFoods()
            foodStore.identifyAllergenIngredients()
            result= foodStore.identifyNonAllergenIngredients().count()
        }
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {

        }
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }
}