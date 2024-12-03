package com.skyecodes.aoc2024

object Day03 : Day<String>(3) {
    override fun parseInput(): String = readLines().joinToString("")
    override fun solvePart1(input: String): Any = "mul\\((\\d+),(\\d+)\\)".toRegex().findAll(input).sumOf { it.groupValues.let { (_, a, b) -> a.toInt() * b.toInt() } }
    override fun solvePart2(input: String): Any {
        var enabled = true
        return "mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)".toRegex().findAll(input).sumOf {
            when (it.value.substring(0, 3)) {
                "do(" -> enabled = true
                "don" -> enabled = false
                "mul" if enabled -> return@sumOf it.groupValues.let { (_, a, b) -> a.toInt() * b.toInt() }
            }
            0
        }
    }
}