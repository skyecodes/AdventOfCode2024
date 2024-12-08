package com.skyecodes.aoc2024

import com.skyecodes.aoc2024.Day08.Input

object Day08 : Day<Input>(8) {
    override fun parseInput(): Input = readLines().let { lines ->
        Input(
            frequencies = lines.flatMapIndexed { y, line ->
                line.mapIndexed { x, c -> Point(x, y) to c }.filter { (_, frequency) -> frequency.isLetterOrDigit() }
            }.groupBy({ (_, frequency) -> frequency }) { (point, _) -> point }.values,
            max = Point(lines[0].length, lines.size)
        )
    }

    override fun solvePart1(input: Input): Any = solve(input) { a, b ->
        (a + a - b).let { if (it.withinBounds(input.max)) listOf(it) else emptyList() }
    }

    override fun solvePart2(input: Input): Any = solve(input) { a, b ->
        generateSequence(a) { it + a - b }.takeWhile { it.withinBounds(input.max) }.toList()
    }

    private fun solve(input: Input, antinodes: (Point, Point) -> List<Point>) = input.frequencies.flatMap { points ->
        generateCombinations(points, 2).filter { (a, b) -> a != b }.flatMap { (a, b) -> antinodes(a, b) }
    }.distinct().size

    data class Input(val frequencies: Collection<List<Point>>, val max: Point)
}