package mpdev.springboot.aoc2020.day20

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day20.Day20
import mpdev.springboot.aoc2020.solutions.day20.JigsawSolver
import mpdev.springboot.aoc2020.solutions.day20.Transformation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day20Test {

    private val day = 20                                    ///////// Update this for a new dayN test
    private val puzzleSolver = Day20()                      ///////// Update this for a new dayN test
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
    fun `Reads Input and Sets up Jigsaw Solver`() {
        val jigsaw = JigsawSolver(inputLines)
        jigsaw.printTiles()
        assertThat(jigsaw.sideSize).isEqualTo(3)
        assertThat(jigsaw.jigsawSize).isEqualTo(9)
        assertThat(jigsaw.tiles.values.first().getDimensions()).isEqualTo(Pair(10,10))
    }

    @Test
    @Order(3)
    fun `Flips tile horizontally and vertically`() {
        val jigsaw = JigsawSolver(inputLines)
        val tile = jigsaw.tiles.entries.first().value
        tile.print()
        val left = tile.getData().keys.filter { it.x == 0 }.map { it.y }
        val bottom = tile.getData().keys.filter { it.y == 9 }.map { it.x }
        println("\nFlip vertically")
        val flippedV = Transformation.apply(tile, Transformation.FLIP_V).also { it.print() }
        val right = flippedV.getData().keys.filter { it.x == 9 }.map { it.y }
        assertThat(left).isEqualTo(right)
        println("\nFlip horizontally")
        val flippedH = Transformation.apply(tile, Transformation.FLIP_H).also { it.print() }
        val top = flippedH.getData().keys.filter { it.y == 0 }.map { it.x }
        assertThat(bottom).isEqualTo(top)
    }

    @Test
    @Order(4)
    fun `Rotates tile horizontally and vertically`() {
        val jigsaw = JigsawSolver(inputLines)
        val tile = jigsaw.tiles.entries.first().value
        tile.print()
        val top = tile.getData().keys.filter { it.y == 0 }.map { it.x }
        println("\nRotate 1")
        val rotate1 = Transformation.apply(tile, Transformation.ROTATE_1).also { it.print() }
        val right = rotate1.getData().keys.filter { it.x == 9 }.map { it.y }
        assertThat(right).isEqualTo(top)
        println("\nRotate 2")
        val rotate2 = Transformation.apply(tile, Transformation.ROTATE_2).also { it.print() }
        val bottom = rotate2.getData().keys.filter { it.y == 9 }.map { it.x }
        assertThat(bottom.map{ 9-it }).isEqualTo(top)
        println("\nRotate 2")
        val rotate3 = Transformation.apply(tile, Transformation.ROTATE_3).also { it.print() }
        val left = rotate3.getData().keys.filter { it.x == 0 }.map { it.y }
        assertThat(left.map { 9-it }).isEqualTo(top)
    }

    @Test
    @Order(5)
    fun `Solves Jigsaw`() {
        val jigsaw = JigsawSolver(inputLines)
        println("transformed tiles: ${jigsaw.transformedTiles.size}")
        jigsaw.transformedTiles.forEach {
            println(it.key); it.value.print()
        }
        jigsaw.solve()
        println(jigsaw.solutions.size)
        jigsaw.solutions.forEach { println(it) }
        println(jigsaw.getSolutionCornerIds())
        val result = jigsaw.getSolutionCornerIds().fold(1L) { acc, i -> acc * i }
        println(result)
        assertThat(result).isEqualTo(20899048083289)
    }

    @Test
    @Order(6)
    fun `Solves Part 1`() {
        val res = puzzleSolver.solvePart1()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("20899048083289")
    }

    @Test
    @Order(8)
    fun `Solves Part 2`() {
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("12")
    }

}
