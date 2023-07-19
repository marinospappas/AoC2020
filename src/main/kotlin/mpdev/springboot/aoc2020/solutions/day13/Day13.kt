package mpdev.springboot.aoc2020.solutions.day13

import mpdev.springboot.aoc2020.model.PuzzlePartSolution
import mpdev.springboot.aoc2020.solutions.PuzzleSolver
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Component
class Day13: PuzzleSolver() {


    final override fun setDay() {
        day = 13
    }

    init {
        setDay()
    }

    var result = 0
    lateinit var timetable: BusTimeTable
    var departureTime: Int = 0

    override fun initSolver(): Pair<Long,String> {
        val elapsed = measureTimeMillis {
            departureTime = inputData[0].toInt()
            timetable = BusTimeTable(inputData[1])
        }
        return Pair(elapsed, "milli-sec")
    }

    override fun solvePart1(): PuzzlePartSolution {
        val elapsed = measureTimeMillis {
            timetable.fillTimetable(departureTime)
            result = timetable.timetableData.minBy { e -> e.value.last() - departureTime }
                .let { it.key * (it.value.last() - departureTime) }
        }
        return PuzzlePartSolution(1, result.toString(), elapsed)
    }

    override fun solvePart2(): PuzzlePartSolution {
        val result: Long
        val elapsed = measureTimeMillis {
            val lastBusIndex = timetable.busSchedule.lastIndex
            val lastBusId = timetable.busSchedule.last().second
            val lastTimeStampDiff = timetable.busSchedule.last().first
            result = timetable.busesSequence(lastBusIndex).take(1).first() * lastBusId - lastTimeStampDiff
        }
        log.info("part 2 recursion level {}", timetable.recursionLevel)
        return PuzzlePartSolution(2, result.toString(), elapsed)
    }

}