package com.skyecodes.aoc2024

import com.skyecodes.aoc2024.Day07.Equation

object Day07 : Day<List<Equation>>(7) {
    override fun parseInput(): List<Equation> =
        readLines().split(": ").map { (result, numbers) -> Equation(result.toLong(), numbers.split(" ").toLong()) }

    private val operators = listOf<Operator>(Long::plus, Long::times)

    override fun solvePart1(input: List<Equation>): Any = solve(input, operators)

    override fun solvePart2(input: List<Equation>): Any = solve(input, operators + { a, b -> "$a$b".toLong() })

    private fun solve(input: List<Equation>, operators: List<Operator>): Long = input.filter { eq ->
        selfCartesianProduct(operators, eq.numbers.size - 1).any { ops ->
            eq.numbers.reduceIndexed { i, acc, num -> ops[i - 1](acc, num) } == eq.result
        }
    }.sumOf { it.result }

    data class Equation(val result: Long, val numbers: List<Long>)
}

private typealias Operator = (Long, Long) -> Long