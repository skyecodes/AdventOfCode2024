package com.skyecodes.aoc2024

object Day11 : Day<List<Long>>(11) {
    override fun parseInput(): List<Long> = lines[0].split(" ").map { it.toLong() }

    override fun solvePart1(input: List<Long>): Number =
        input.repeatMap(25) {
            flatMap {
                val str = it.toString()
                when {
                    it == 0L -> listOf(1L)
                    str.length % 2 == 0 -> str.chunked(str.length / 2).map { it.toLong() }
                    else -> listOf(it * 2024)
                }
            }
        }.size

    override fun solvePart2(input: List<Long>): Number =
        input.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.repeatMap(75) {
            map { (stone, count) ->
                val str = stone.toString()
                when {
                    stone == 0L -> mapOf(1L to count)
                    str.length % 2 == 0 -> str.chunked(str.length / 2).map { it.toLong() }
                        .let { if (it[0] == it[1]) mapOf(it[0] to count * 2) else it.associateWith { count } }

                    else -> mapOf(stone * 2024 to count)
                }
            }.fold(mutableMapOf<Long, Long>()) { acc, m ->
                m.forEach { (k, v) -> acc.merge(k, v, Long::plus) }
                acc
            }
        }.values.sum()
}