package com.skyecodes.aoc2024

private typealias Input = List<List<Long>>

object Day13 : Day<Input>(13) {
    override fun parseInput(): Input = lines.chunked(4).map {
        it.take(3).map { s -> s.split(": ")[1].split(", ").map { it.substring(2).toLong() } }.flatten()
    }

    override fun solvePart1(input: Input): Number = input.solve()

    override fun solvePart2(input: Input): Number = input.map { (ax, ay, bx, by, px, py) ->
        listOf(ax, ay, bx, by, px + 10000000000000, py + 10000000000000)
    }.solve()

    fun Input.solve(): Long = sumOf { (ax, ay, bx, by, px, py) ->
        val ca = (px * by - py * bx) / (ax * by - ay * bx).toDouble()
        val cb = (px - ax * ca) / bx
        if (ca % 1 == 0.0 && cb % 1 == 0.0) (ca * 3 + cb).toLong() else 0
    }
}