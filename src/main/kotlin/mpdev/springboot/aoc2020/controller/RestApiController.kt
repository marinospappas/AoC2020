package mpdev.springboot.aoc2020.controller

import mpdev.springboot.aoc2020.model.PuzzleSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/aoc2020")
class RestApiController(@Autowired var puzzleSolvers: Map<Int,PuzzleSolver>) {

    @GetMapping("/day/{day}", produces = ["application/json"])
    fun getPuzzleSolution(@PathVariable day: Int): PuzzleSolution {
        return puzzleSolvers[day]?.solve() ?: PuzzleSolution(message = "Solution not implemented yet", day = day, solution = setOf())
    }

}