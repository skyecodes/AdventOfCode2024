package com.skyecodes.aoc2024

object Day02 : Day<Input>(2) {
    override fun parseInput(): Input = readLines().splitToInt(" ")
    override fun solvePart1(input: Input): Any = input.count { line -> line.isSafe() }
    override fun solvePart2(input: Input): Any = input.count { line -> line.isSafe() || (0..line.lastIndex).any { i -> line.removeAt(i).isSafe() } }
    private fun List<Int>.isSafe(): Boolean = zipWithNext { a, b -> b - a }.run { all { it > 0 && it <= 3 } || all { it < 0 && it >= -3 } }
}

private typealias Input = List<List<Int>>
