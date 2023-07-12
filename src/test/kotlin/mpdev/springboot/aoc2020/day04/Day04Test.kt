package mpdev.springboot.aoc2020.day04

import mpdev.springboot.aoc2020.input.InputDataReader
import mpdev.springboot.aoc2020.solutions.day04.Day04
import mpdev.springboot.aoc2020.solutions.day04.Passport
import mpdev.springboot.aoc2020.solutions.day04.PassportDb
import mpdev.springboot.aoc2020.solutions.day04.ValidationGroups.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day04Test {

    private val day = 4                                       ///////// Update this for a new dayN test
    private val puzzleSolver = Day04()                        ///////// Update this for a new dayN test
    private val inputDataReader = InputDataReader("src/test/resources/inputdata/input")
    private var inputLines: List<String> = inputDataReader.read(day)

    private var passport = Passport()

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
    fun `Reads Input and Sets up Passport Db correctly`() {
        val passportDb = PassportDb(inputLines)
        passportDb.data.forEach { passport -> println(passport)  }
        assertThat(passportDb.data.size).isEqualTo(4)
    }

    @ParameterizedTest
    @MethodSource("validatePart1Params")
    @Order(4)
    fun `Validates Passports Part 1`(setter: (String?) -> Unit, expected: String) {
        val passportDb = PassportDb(inputLines)
        passport = randomValidPassport()
        setter(null)
        val validationResult = passportDb.validatePassport(passport, Part1::class.java)
        assertThat(validationResult.size).isEqualTo(1)
        assertTrue(validationResult.first().message.startsWith(expected))
    }

    @ParameterizedTest
    @MethodSource("validatePart1Params")
    @Order(5)
    fun `Part 1 Validations are also executed in Part 2`(setter: (String?) -> Unit, expected: String) {
        val passportDb = PassportDb(inputLines)
        passport = randomValidPassport()
        setter(null)
        val validationResult = passportDb.validatePassport(passport, Part2::class.java)
        assertThat(validationResult.size).isEqualTo(1)
        assertTrue(validationResult.first().message.startsWith(expected))
    }

    private fun validatePart1Params() =
        Stream.of(
            Arguments.of({ v: String? -> passport.birthYear = v } , "EX100:"),
            Arguments.of({ v: String? -> passport.issueYear = v } , "EX101:"),
            Arguments.of({ v: String? -> passport.expirationYear = v } , "EX102:"),
            Arguments.of({ v: String? -> passport.height = v } , "EX103:"),
            Arguments.of({ v: String? -> passport.hairColor = v } , "EX104:"),
            Arguments.of({ v: String? -> passport.eyeColor = v } , "EX105:"),
            Arguments.of({ v: String? -> passport.passportID = v } , "EX106:")
        )

    @Test
    @Order(6)
    fun `Counts Valid Passports Part 1`() {
        val passportDb = PassportDb(inputLines)
        val res = passportDb.data.count { passport -> passportDb.validatePassport(passport, Part1::class.java).isEmpty() }
        assertThat(res).isEqualTo(2)
    }

    @Test
    @Order(7)
    fun `Solves Part 1`() {
        assertThat(puzzleSolver.solvePart1().result).isEqualTo("2")
    }

    @ParameterizedTest
    @MethodSource("validatePart2Params")
    @Order(8)
    fun `Validates Passports Part 2`(setter: (String?) -> Unit, value: String, expected: String) {
        val passportDb = PassportDb(inputLines)
        passport = randomValidPassport()
        setter(value)
        val validationResult = passportDb.validatePassport(passport, Part2::class.java)
        assertThat(validationResult.size).isEqualTo(1)
        assertTrue(validationResult.first().message.startsWith(expected))
    }

    private fun validatePart2Params() =
        Stream.of(
            Arguments.of({ v: String? -> passport.birthYear = v } , "1910", "EX201:"),
            Arguments.of({ v: String? -> passport.birthYear = v } , "2025", "EX202:"),
            Arguments.of({ v: String? -> passport.issueYear = v } , "2009", "EX203:"),
            Arguments.of({ v: String? -> passport.issueYear = v } , "2021", "EX204:"),
            Arguments.of({ v: String? -> passport.expirationYear = v } , "2019", "EX205:"),
            Arguments.of({ v: String? -> passport.expirationYear = v } , "2031", "EX206:"),
            Arguments.of({ v: String? -> passport.height = v } , "1.56m", "EX207:"),
            Arguments.of({ v: String? -> passport.height = v } , "145cm", "EX209:"),
            Arguments.of({ v: String? -> passport.height = v } , "199cm", "EX209:"),
            Arguments.of({ v: String? -> passport.height = v } , "50in", "EX209:"),
            Arguments.of({ v: String? -> passport.height = v } , "77in", "EX209:"),
            Arguments.of({ v: String? -> passport.hairColor = v } , "#1234x", "EX210:"),
            Arguments.of({ v: String? -> passport.hairColor = v } , "fffabc", "EX210:"),
            Arguments.of({ v: String? -> passport.eyeColor = v } , "yel", "EX211:"),
            Arguments.of({ v: String? -> passport.passportID = v } , "abcd", "EX212:"),
            Arguments.of({ v: String? -> passport.passportID = v } , "1234567890", "EX212:"),
        )

    @Test
    @Order(9)
    fun `Validates Passports Part 2`() {
        val passportDb = PassportDb(inputLines)
        val res = passportDb.data.count { passport -> passportDb.validatePassport(passport, Part2::class.java).isEmpty() }
        assertThat(res).isEqualTo(2)
    }

    @Test
    @Order(10)
    fun `Validates Invalid Passports Part 2`() {
        val passportDb = PassportDb(invalidPassports())
        val res = passportDb.data.count { passport -> passportDb.validatePassport(passport, Part2::class.java).isEmpty() }
        assertThat(res).isEqualTo(0)
    }

    @Test
    @Order(11)
    fun `Validates Valid Passports Part 2`() {
        val passportDb = PassportDb(validPassports())
        val res = passportDb.data.count { passport -> passportDb.validatePassport(passport, Part2::class.java).isEmpty() }
        assertThat(res).isEqualTo(4)
    }

    @Test
    @Order(13)
    fun `Solves Part 2`() {
        val inputList = inputLines.toMutableList()
        inputList.add("")
        inputList.addAll(validPassports())
        inputList.add("")
        inputList.addAll(invalidPassports())
        puzzleSolver.inputData = inputList
        puzzleSolver.initSolver()
        assertThat(puzzleSolver.solvePart2().result).isEqualTo("6")
    }

    private fun randomValidPassport() = Passport("1961", "2015", "2025", "175cm",
        "#fffffd", "gry", "123456789", "ABC")

    private fun invalidPassports() = listOf(
        "eyr:1972 cid:100",
        "hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926",
        "",
        "iyr:2019",
        "hcl:#602927 eyr:1967 hgt:170cm",
        "ecl:grn pid:012533040 byr:1946",
        "",
        "hcl:dab227 iyr:2012",
        "ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277",
        "",
        "hgt:59cm ecl:zzz",
        "eyr:2038 hcl:74454a iyr:2023",
        "pid:3556412378 byr:2007"
    )

    private fun validPassports() = listOf(
        "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980",
        "hcl:#623a2f",
        "",
        "eyr:2029 ecl:blu cid:129 byr:1989",
        "iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm",
        "",
        "hcl:#888785",
        "hgt:164cm byr:2001 iyr:2015 cid:88",
        "pid:545766238 ecl:hzl",
        "eyr:2022",
        "",
        "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719"
    )

}
