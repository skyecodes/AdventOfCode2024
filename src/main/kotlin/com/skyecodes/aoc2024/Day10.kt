package com.skyecodes.aoc2024

import com.skyecodes.aoc2024.Day10.Input

object Day10 : Day<Input>(10) {
    override fun parseInput(): Input = lines.map { it.map { it.digitToInt() } }.let { Input(it, it.findAll(0)) }

    override fun solvePart1(input: Input): Number = solve(input) { mutableSetOf() }

    override fun solvePart2(input: Input): Number = solve(input) { mutableListOf() }

    private fun solve(input: Input, collec: () -> MutableCollection<Point>) =
        input.trailheads.sumOf { advance(input.map, it, 0, 9).toCollection(collec()).size }

    private fun advance(map: Matrix<Int>, pos: Point, value: Int, max: Int): Collection<Point> =
        pos.directSurroundings.flatMap {
            when (val nextValue = map[it]) {
                max if value == max - 1 -> listOf(it)
                value + 1 -> advance(map, it, nextValue, max)
                else -> emptyList()
            }
        }

    data class Input(val map: Matrix<Int>, val trailheads: List<Point>)
}