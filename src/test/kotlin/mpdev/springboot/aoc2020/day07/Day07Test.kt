package mpdev.springboot.aoc2020.day07

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day07.BagKey
import mpdev.springboot.aoc2020.solutions.day07.Day07
import mpdev.springboot.aoc2020.solutions.day07.BagRules
import mpdev.springboot.aoc2020.solutions.day07.mapFromDictionary
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day07Test {

    private val day = 7                                     ///////// Update this for a new dayN test
    private val puzzleSolver = Day07()                        ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Bag Rules correctly`() {
        val bagRules = BagRules(inputLines)
        bagRules.printRules()
        assertThat(bagRules.rulesList.size).isEqualTo(10)
    }
    @Test
    @Order(3)
    fun `Discovers all Parent Bags`() {
        val bagRules = BagRules(inputLines)
        val parents =bagRules.getAllParentsToRoot(BagKey("shiny gold".mapFromDictionary()))
        println(parents)
        assertThat(parents.size).isEqualTo(4)
        assertTrue(parents.contains(BagKey("bright white".mapFromDictionary())))
        assertTrue(parents.contains(BagKey("muted yellow".mapFromDictionary())))
        assertTrue(parents.contains(BagKey("light red".mapFromDictionary())))
        assertTrue(parents.contains(BagKey("dark orange".mapFromDictionary())))
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        assertThat(puzzleSolver.solvePart1().result).isEqualTo("4")
    }

    @Test
    @Order(5)
    fun `Discovers all Contents`() {
        val bagRules = BagRules(inputLines)
        val contents = bagRules.getBagContents(BagKey("shiny gold".mapFromDictionary()))
        println(contents)
        assertThat(contents.filter { it.second != BagKey("shiny gold".mapFromDictionary()) }.size).isEqualTo(6)
        assertThat(contents.filter { it.second == BagKey("dark olive".mapFromDictionary()) }.size).isEqualTo(1)
        assertThat(contents.filter { it.second == BagKey("vibrant plum".mapFromDictionary()) }.size).isEqualTo(1)
        assertThat(contents.filter { it.second == BagKey("dotted black".mapFromDictionary()) }.size).isEqualTo(2)
        assertThat(contents.filter { it.second == BagKey("faded blue".mapFromDictionary()) }.size).isEqualTo(2)
        assertThat(contents.filter { it.second != BagKey("shiny gold".mapFromDictionary()) }.sumOf { it.first }).isEqualTo(32)
    }

    @Test
    @Order(5)
    fun `Discovers all Contents - 2`() {
        val bagRules = BagRules(input2())
        val contents = bagRules.getBagContents(BagKey("shiny gold".mapFromDictionary()))
        println(contents)
        assertThat(contents.filter { it.second != BagKey("shiny gold".mapFromDictionary()) }.size).isEqualTo(6)
        assertThat(contents.filter { it.second != BagKey("shiny gold".mapFromDictionary()) }.sumOf { it.first }).isEqualTo(126)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        assertThat(puzzleSolver.solvePart2().result).isEqualTo("32")
    }

    private fun input2() = listOf(
        "shiny gold bags contain 2 dark red bags.",
        "dark red bags contain 2 dark orange bags.",
        "dark orange bags contain 2 dark yellow bags.",
        "dark yellow bags contain 2 dark green bags.",
        "dark green bags contain 2 dark blue bags.",
        "dark blue bags contain 2 dark violet bags.",
        "dark violet bags contain no other bags."
    )
}
