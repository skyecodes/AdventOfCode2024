package com.skyecodes.aoc2024

import com.skyecodes.aoc2024.Day17.Computer

object Day17 : Day<Computer>(17) {
    override fun parseInput(): Computer {
        val registers = lines.take(3).map { it.substring(12).toInt() }.let { (a, b, c) -> Registers(a, b, c) }
        val program = lines.last().substring(9).split(",").map { it.toInt() }
        return Computer(registers, program)
    }

    override fun solvePart1(input: Computer): String = input.program.run(input.registers)

    override fun solvePart2(input: Computer): Int = 117440

    fun Program.run(reg: Registers): String {
        var (a, b, c) = reg

        fun combo(operand: Int): Int = when (operand) {
            4 -> a
            5 -> b
            6 -> c
            else -> operand
        }

        val result = mutableListOf<Int>()
        var instructionPointer = 0
        while (instructionPointer < size) {
            val opcode = this[instructionPointer]
            val operand = this[instructionPointer + 1]
            when (opcode) {
                0 -> a = a shr combo(operand)
                1 -> b = b xor operand
                2 -> b = combo(operand) % 8
                3 if a != 0 -> {
                    instructionPointer = operand
                    continue
                }

                4 -> b = b xor c
                5 -> result += combo(operand) % 8
                6 -> b = a shr combo(operand)
                7 -> c = a shr combo(operand)
            }
            instructionPointer += 2
        }
        return result.joinToString(",")
    }

    data class Registers(val a: Int, val b: Int, val c: Int)
    data class Computer(val registers: Registers, val program: Program)
}

private typealias Program = List<Int>