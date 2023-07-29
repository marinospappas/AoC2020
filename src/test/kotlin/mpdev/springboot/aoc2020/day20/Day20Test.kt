package mpdev.springboot.aoc2020.day20

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day20.Day20
import mpdev.springboot.aoc2020.solutions.day20.JigsawSolver
import mpdev.springboot.aoc2020.solutions.day20.TilePixel
import mpdev.springboot.aoc2020.solutions.day20.Transformation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
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
        println("Input Tiles")
        jigsaw.printTiles()
        println("Tiles without Borders")
        jigsaw.tilesNoBorders.forEach { println("Tile ${it.key} no border"); it.value.print() }
        assertThat(jigsaw.sideSize).isEqualTo(3)
        assertThat(jigsaw.jigsawSize).isEqualTo(9)
        assertTrue(jigsaw.tiles.values.all{ it.getDimensions() == Pair(10,10) })
        assertThat(jigsaw.transformedTiles.size).isEqualTo(72)
        assertThat(jigsaw.transformedTilesPerimeter.size).isEqualTo(72)
        assertTrue(jigsaw.transformedTilesPerimeter.values.all { it.size == 4 })
        assertThat(jigsaw.tilesNoBorders.size).isEqualTo(9)
        assertTrue(jigsaw.tilesNoBorders.values.all{ it.getDimensions() == Pair(8,8) })
        jigsaw.shapeToMatch.print()
        assertThat(jigsaw.shapeToMatch.getDimensions()).isEqualTo(Pair(20,3))
        assertThat(jigsaw.shapeToMatch.getData().size).isEqualTo(15)
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
        val flippedV = Transformation.apply(tile, Transformation.FLIP_V, 10, 10).also { it.print() }
        val right = flippedV.getData().keys.filter { it.x == 9 }.map { it.y }
        assertThat(left).isEqualTo(right)
        println("\nFlip horizontally")
        val flippedH = Transformation.apply(tile, Transformation.FLIP_H, 10, 10).also { it.print() }
        val top = flippedH.getData().keys.filter { it.y == 0 }.map { it.x }
        assertThat(bottom).isEqualTo(top)
    }

    @Test
    @Order(4)
    fun `Rotates tile 1, 2, 3 times`() {
        val jigsaw = JigsawSolver(inputLines)
        val tile = jigsaw.tiles.entries.first().value
        tile.print()
        val top = tile.getData().keys.filter { it.y == 0 }.map { it.x }
        println("\nRotate 1")
        val rotate1 = Transformation.apply(tile, Transformation.ROTATE_1, 10, 10).also { it.print() }
        val right = rotate1.getData().keys.filter { it.x == 9 }.map { it.y }
        assertThat(right).isEqualTo(top)
        println("\nRotate 2")
        val rotate2 = Transformation.apply(tile, Transformation.ROTATE_2, 10, 10).also { it.print() }
        val bottom = rotate2.getData().keys.filter { it.y == 9 }.map { it.x }
        assertThat(bottom.map{ 9-it }).isEqualTo(top)
        println("\nRotate 2")
        val rotate3 = Transformation.apply(tile, Transformation.ROTATE_3, 10, 10).also { it.print() }
        val left = rotate3.getData().keys.filter { it.x == 0 }.map { it.y }
        assertThat(left.map { 9-it }).isEqualTo(top)
    }

    @Test
    @Order(5)
    fun `Solves Jigsaw`() {
        val jigsaw = JigsawSolver(inputLines)
        jigsaw.solve()
        println(jigsaw.solutions.size)
        jigsaw.solutions.forEach { println(it) }
        println()
        println(jigsaw.getSolution())
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
    @Order(7)
    fun `Constructs picture of jigsaw from tiles list and finds monsters`() {
        val jigsaw = JigsawSolver(inputLines)
        jigsaw.solve()
        (0..2).forEach { i ->
            (0..2).forEach { j -> print("${jigsaw.getSolution()[3*i + j]},  ") }
            println()
        }
        val picture = jigsaw.constructJigsaw()
        picture.print()
        assertThat(picture.getDimensions()).isEqualTo(Pair(24,24))
        val foundMonsters = jigsaw.matchShape(picture)
        foundMonsters?.print() ?: println("no solution")
        assertThat(foundMonsters!!.countOf(TilePixel.WHITE)).isEqualTo(273)
    }

    @Test
    @Order(9)
    fun `Solves Part 2`() {
        puzzleSolver.solvePart1()   // must be executed to solve the puzzle first
        val res = puzzleSolver.solvePart2()
        println("Elapsed time: ${res.elapsedTime} ${res.timeUnit}")
        assertThat(res.result).isEqualTo("273")
    }

}
