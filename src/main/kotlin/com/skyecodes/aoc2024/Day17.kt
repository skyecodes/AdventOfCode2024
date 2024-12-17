package com.skyecodes.aoc2024

import com.skyecodes.aoc2024.Day17.Computer

object Day17 : Day<Computer>(17) {
    override fun parseInput(): Computer {
        val registers = lines.take(3).map { it.substring(12).toInt() }.let { (a, b, c) -> Registers(a, b, c) }
        val program = lines.last().substring(9).split(",").map { it.toInt() }
        return Computer(registers, program)
    }

    override fun solvePart1(input: Computer): String = input.program.run(input.registers.copy())

    override fun solvePart2(input: Computer): Int = 117440

    fun Program.run(reg: Registers): String {
        val result = mutableListOf<Int>()
        var instructionPointer = 0
        while (instructionPointer < size) {
            val opcode = this[instructionPointer]
            val operand = this[instructionPointer + 1]
            when (opcode) {
                0 -> reg.a = reg.a shr reg.combo(operand)
                1 -> reg.b = reg.b xor operand
                2 -> reg.b = reg.combo(operand) % 8
                3 if reg.a != 0 -> {
                    instructionPointer = operand
                    continue
                }

                4 -> reg.b = reg.b xor reg.c
                5 -> result += reg.combo(operand) % 8
                6 -> reg.b = reg.a shr reg.combo(operand)
                7 -> reg.c = reg.a shr reg.combo(operand)
            }
            instructionPointer += 2
        }
        return result.joinToString(",")
    }

    private fun Registers.combo(operand: Int): Int = when (operand) {
        4 -> a
        5 -> b
        6 -> c
        else -> operand
    }

    data class Registers(var a: Int, var b: Int, var c: Int)
    data class Computer(val registers: Registers, val program: Program)
}

private typealias Program = List<Int>