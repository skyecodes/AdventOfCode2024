package com.skyecodes.aoc2024

import com.skyecodes.aoc2024.Day05.Input

object Day05 : Day<Input>(5) {
    override fun parseInput(): Input = lines.partition { '|' in it }.let { (rules, updates) ->
        Input(
            rules = rules.splitToInt("|").map { (a, b) -> a to b },
            updates = updates.filter { it.isNotEmpty() }.splitToInt(",")
        )
    }

    override fun solvePart1(input: Input): Number = input.updates.filter { update ->
        input.rules.filterInUpdate(update).all { (a, b) -> update.indexOf(a) < update.indexOf(b) }
    }.sumOfMiddle()

    override fun solvePart2(input: Input): Number = input.updates.filter { update ->
        input.rules.filterInUpdate(update).any { (a, b) -> update.indexOf(a) > update.indexOf(b) }
    }.map { update ->
        update.sortedWith { a, b ->
            when {
                a to b in input.rules -> 1
                b to a in input.rules -> -1
                else -> 0
            }
        }
    }.sumOfMiddle()

    private fun List<Rule>.filterInUpdate(update: Update) = filter { (a, b) -> a in update && b in update }
    private fun List<Update>.sumOfMiddle() = sumOf { it[it.lastIndex / 2] }

    data class Input(
        val rules: List<Rule>,
        val updates: List<Update>
    )
}

private typealias Rule = Pair<Int, Int>
private typealias Update = List<Int>