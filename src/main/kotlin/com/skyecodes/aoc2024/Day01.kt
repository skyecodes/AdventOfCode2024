package com.skyecodes.aoc2024

import kotlin.math.abs

object Day01 : Day<Pair<List<Int>, List<Int>>>(1) {
    override fun parseInput(): Pair<List<Int>, List<Int>> =
        (mutableListOf<Int>() to mutableListOf<Int>()).apply {
            readLines()
                .map { line -> line.split("   ").let { (a, b) -> a.toInt() to b.toInt() } }
                .forEach { (a, b) -> first += a; second += b }
        }

    override fun solvePart1(input: Pair<List<Int>, List<Int>>): Any {
        val (listA, listB) = input.let { it.first.sorted() to it.second.sorted() }
        return listA.foldIndexed(0) { index, acc, a ->
            acc + abs(a - listB[index])
        }
    }

    override fun solvePart2(input: Pair<List<Int>, List<Int>>): Any {
        val (listA, listB) = input
        val occurenceMap = listB.groupingBy { it }.eachCount()
        return listA.fold(0) { acc, a ->
            acc + a * (occurenceMap[a] ?: 0)
        }
    }
}