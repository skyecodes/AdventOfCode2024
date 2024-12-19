package com.skyecodes.aoc2024

object Day19 : Day<Day19.Input>(19) {

    data class Input(val patterns: List<String>, val designs: List<String>)

    override fun parseInput(): Input {
        val patterns = lines[0].split(", ").sortedByDescending { it.length }
        val designs = lines.drop(2)
        return Input(patterns, designs)
    }

    override fun solvePart1(input: Input): Int = input.designs.count { design ->
        isPossible(design, input.patterns)
    }

    private fun isPossible(s: String, patterns: List<String>): Boolean = when {
        s.isEmpty() -> true
        else -> s.indices.map { s.dropLast(it) }.any {
            it in patterns && isPossible(s.substring(it.length), patterns)
        }
    }

    override fun solvePart2(input: Input): Long = input.designs.sumOf { design ->
        countPossibilities(design, input.patterns, mutableMapOf())
    }

    private fun countPossibilities(s: String, patterns: List<String>, cache: MutableMap<String, Long>): Long = when {
        s.isEmpty() -> 1
        s in cache -> cache.getValue(s)
        else -> s.indices.map { s.dropLast(it) }.sumOf {
            if (it !in patterns) 0
            else s.substring(it.length).let {
                cache.getOrPut(it) { countPossibilities(it, patterns, cache) }
            }
        }
    }
}