package com.skyecodes.aoc2024

import com.skyecodes.aoc2024.Day01.Input
import kotlin.math.abs

object Day01 : Day<Input>(1) {
    override fun parseInput(): Input =
        Input().apply { readLines().splitToInt("   ").forEach { (a, b) -> listA += a; listB += b } }

    override fun solvePart1(input: Input): Any {
        val (listA, listB) = input.listA.sorted() to input.listB.sorted()
        return listA.foldIndexed(0) { index, acc, a ->
            acc + abs(a - listB[index])
        }
    }

    override fun solvePart2(input: Input): Any {
        val (listA, listB) = input
        val occurenceMap = listB.groupingBy { it }.eachCount()
        return listA.fold(0) { acc, a ->
            acc + a * (occurenceMap[a] ?: 0)
        }
    }

    data class Input(
        val listA: MutableList<Int> = mutableListOf(),
        val listB: MutableList<Int> = mutableListOf(),
    )
}