package mpdev.springboot.aoc2020.solutions.day08

import mpdev.springboot.aoc2020.utils.AocException

class GameConsole(val input: List<String>) {

    companion object {
        const val CODE_SUCCESS = 0
        const val CODE_ERROR = -1
    }

    var bootProgram: List<Pair<OpCode,Int>>
    private var registers: Registers = Registers()

    init {
       bootProgram = input.map { processInputLine(it) }
    }

    fun runBootProg(program: List<Pair<OpCode,Int>> = bootProgram): Int {
        val pcValues = mutableListOf(0)
        registers = Registers()
        while (true) {
            val (operation, argument) = program[registers.PC]
            registers = operation.execute(registers, argument)
            if (pcValues.contains(registers.PC))     // stop when a loop is detected
                return CODE_ERROR       // error exit code
            pcValues.add(registers.PC)
            if (registers.PC >= program.size)       // end of program
                return CODE_SUCCESS        // success exit code
        }
    }

    fun repairBoorProg(): Int {
        var exitCode = CODE_ERROR
        for (i in bootProgram.indices) {
            if (bootProgram[i].first == OpCode.ACC)
                continue
            val program = bootProgram.toMutableList()
            val op = program[i]
            program[i] = if (op.first == OpCode.NOP) Pair(OpCode.JMP, op.second)
                        else Pair(OpCode.NOP, op.second)
            exitCode = runBootProg(program)
            if (exitCode == CODE_SUCCESS)
                return CODE_SUCCESS
        }
        return exitCode
    }

    fun getAcc() = registers.A
    fun getPc() = registers.PC

    private fun processInputLine(line: String): Pair<OpCode,Int> {
        val match = Regex("""([a-z]+) ([-+]\d+)""").find(line)
        try {
            val (operation, argument) = match!!.destructured
            return Pair(OpCode.from(operation), argument.toInt())
        } catch (e: Exception) {
            throw AocException("bad input line $line")
        }
    }
}