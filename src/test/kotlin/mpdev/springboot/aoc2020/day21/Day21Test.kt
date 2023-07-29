package mpdev.springboot.aoc2020.day21

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day21.Day21
import mpdev.springboot.aoc2020.solutions.day21.FoodStore
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day21Test {

    private val day = 21                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day21()                      ///////// Update this for a new dayN test
    private val inputDataReader = InputDataReader("src/test/resources/inputdata/input")
    private var inputLines: List<String> = inputDataReader.read(day)

    @BeforeEach
    fun setup() {
        puzzleSolver.setDay()
        puzzleSolver.inputData = inputLines
        puzzleSolver.initSolver()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(puzzleSolver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads Input and Sets up Foods List`() {
        val foodStore = FoodStore(inputLines)
        foodStore.foodsList.forEach { println(it) }
        println("allergens: ${foodStore.allergens.keys}")
        println("*** additional info")
        println("number of items in food list: ${foodStore.foodsList.size}")
        println("max number of ingredients in any food: ${foodStore.foodsList.maxOf { it.ingredients.size }}")
        println("max number of allergens in any food: ${foodStore.foodsList.maxOf { it.allergens.size }}")
        assertThat(foodStore.foodsList.size).isEqualTo(4)
        assertThat(foodStore.foodsList[0].ingredients.size).isEqualTo(4)
        assertThat(foodStore.foodsList[0].allergens.size).isEqualTo(2)
        assertThat(foodStore.foodsList[3].ingredients.size).isEqualTo(3)
        assertThat(foodStore.foodsList[3].allergens.size).isEqualTo(1)
    }

    @Test
    @Order(3)
    fun `Identifies known allergens and non allergen ingredients`() {
        val foodStore = FoodStore(inputLines)
        println("* find allergens")
        foodStore.findAllergensFromFoods()
        foodStore.allergens.forEach { println(it) }
        println("* identify allergen ingredients")
        foodStore.identifyAllergenIngredients()
        foodStore.allergens.forEach { println(it) }
        println("* identify non allergen ingredients")
        val nonAllergens = foodStore.identifyNonAllergenIngredients().also { println(it) }
        assertThat(nonAllergens.size).isEqualTo(5)
        assertThat(nonAllergens.count { it == "sbzzf" }).isEqualTo(2)
        assertThat(nonAllergens.count { it == "kfcds" }).isEqualTo(1)
        assertThat(nonAllergens.count { it == "nhms" }).isEqualTo(1)
        assertThat(nonAllergens.count { it == "trh" }).isEqualTo(1)
    }

    @Test
    @Order(6)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("5")
    }

    @Test
    @Order(9)
    fun `Solves Part 2`() {
        puzzleSolver.solvePart1()   // must be executed to solve the puzzle first
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("")
    }

}
