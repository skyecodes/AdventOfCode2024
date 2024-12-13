package com.skyecodes.aoc2024

private typealias Input13 = List<List<Long>>

object Day13 : Day<Input13>(13) {
    override fun parseInput(): Input13 = lines.chunked(4).map {
        it.take(3).map { s -> s.split(": ")[1].split(", ").map { it.substring(2).toLong() } }.flatten()
    }

    override fun solvePart1(input: Input13): Number = input.solve()

    override fun solvePart2(input: Input13): Number =
        input.map { it.mapIndexed { i, n -> if (i > 3) n + 10000000000000 else n } }.solve()

    fun Input13.solve(): Long = sumOf { (ax, ay, bx, by, px, py) ->
        val ca = (px * by - py * bx) / (ax * by - ay * bx).toDouble()
        val cb = (px - ax * ca) / bx
        if (ca % 1 == 0.0 && cb % 1 == 0.0) (ca * 3 + cb).toLong() else 0
    }
}