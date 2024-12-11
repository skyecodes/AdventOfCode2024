package com.skyecodes.aoc2024

object Day02 : Day<Matrix<Int>>(2) {
    override fun parseInput(): Matrix<Int> = lines.splitToInt(" ")
    override fun solvePart1(input: Matrix<Int>): Number = input.count { line -> line.isSafe() }
    override fun solvePart2(input: Matrix<Int>): Number =
        input.count { line -> line.isSafe() || (0..line.lastIndex).any { i -> line.removeAt(i).isSafe() } }
    private fun List<Int>.isSafe(): Boolean = zipWithNext { a, b -> b - a }.run { all { it > 0 && it <= 3 } || all { it < 0 && it >= -3 } }
}
