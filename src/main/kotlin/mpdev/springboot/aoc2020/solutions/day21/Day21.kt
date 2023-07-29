package mpdev.springboot.aoc2020.solutions.day21

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureNanoTime

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
        val elapsed = measureNanoTime {
            foodStore = FoodStore(inputData)
        }
        return Pair(elapsed/1000, "micro-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureNanoTime {
            foodStore.findAllergensFromFoods()
            foodStore.identifyAllergenIngredients()
            result= foodStore.identifyNonAllergenIngredients().count()
        }
        return PuzzlePartSolution(1, result.toString(), elapsed/1000, "micro-sec")
    }

    override fun solvePart2(): PuzzlePartSolution {
        var result: String
        val elapsed = measureNanoTime {
            result = foodStore.allergens.toSortedMap().map { it.value.first() }.joinToString(",")
        }
        return PuzzlePartSolution(2, result, elapsed/1000, "micro-sec")
    }
}