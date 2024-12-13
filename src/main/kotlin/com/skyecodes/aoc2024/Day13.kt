package com.skyecodes.aoc2024

import com.skyecodes.aoc2024.Day13.Machine

object Day13 : Day<List<Machine>>(13) {
    override fun parseInput(): List<Machine> = lines.chunked(4).map {
        it.take(3).map { s ->
            s.split(": ")[1].split(", ").map { it.substring(2).toInt() }.let { (x, y) -> Point(x, y) }
        }.let { (a, b, prize) -> Machine(a, b, prize) }
    }

    override fun solvePart1(input: List<Machine>): Number = input.sumOf { machine ->
        generateCombinations((0..100).toList(), 2)
            .filter { (a, b) -> machine.buttonA * a + machine.buttonB * b == machine.prize }
            .minOfOrNull { (a, b) -> 3 * a + b } ?: 0
    }

    override fun solvePart2(input: List<Machine>): Number = 0

    data class Machine(val buttonA: Point, val buttonB: Point, val prize: Point)
}