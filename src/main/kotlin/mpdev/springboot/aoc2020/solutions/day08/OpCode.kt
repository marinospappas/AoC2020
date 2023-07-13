package mpdev.springboot.aoc2020.solutions.day08

enum class OpCode(val strValue: String, val execute: (Registers,Int) -> Registers) {

    ACC("acc", { reg,arg -> Registers(reg.A + arg, reg.PC + 1) }),
    JMP("jmp", { reg,arg -> Registers(reg.A, reg.PC + arg) }),
    NOP("nop", { reg,_ -> Registers(reg.A, reg.PC + 1) } );

    companion object {
        fun from(s: String): OpCode = values().first { it.strValue == s }
    }
}

data class Registers(var A: Int = 0, var PC: Int = 0)