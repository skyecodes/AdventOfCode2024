package com.skyecodes.aoc2024

object Day19 : Day<Day19.Input>(19) {
    override fun parseInput(): Input {
        val patterns = lines[0].split(", ").sortedByDescending { it.length }
        val designs = lines.drop(2)
        return Input(patterns, designs)
    }

    override fun solvePart1(input: Input): Int = input.designs.count { design ->
        solve1(design, input.patterns)
    }

    override fun solvePart2(input: Input): Long = input.designs.sumOf { design ->
        solve2(design, input.patterns, mutableMapOf())
    }

    private fun solve1(s: String, patterns: List<String>): Boolean = when {
        s.isEmpty() -> true
        else -> s.indices.map { s.dropLast(it) }.any {
            it in patterns && solve1(s.substring(it.length), patterns)
        }
    }

    private fun solve2(s: String, patterns: List<String>, cache: MutableMap<String, Long>): Long = when {
        s.isEmpty() -> 1
        s in cache -> cache.getValue(s)
        else -> s.indices.map { s.dropLast(it) }.sumOf {
            if (it !in patterns) 0
            else s.substring(it.length).let {
                solve2(it, patterns, cache).also { res -> cache[it] = res }
            }
        }
    }

    data class Input(val patterns: List<String>, val designs: List<String>)
}