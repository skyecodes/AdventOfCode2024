package com.skyecodes.aoc2024

abstract class Day<T : Any>(val number: Int) {
    val lines by lazy {
        this::class.java.getResourceAsStream("/day%02d.txt".format(number))!!.bufferedReader().readLines()
    }

    abstract fun parseInput(): T
    abstract fun solvePart1(input: T): Number
    abstract fun solvePart2(input: T): Number

    fun run(): Result = parseInput().let { Result(solvePart1(it), solvePart2(it)) }

    data class Result(val part1: Number, val part2: Number)
}

abstract class SimpleDay(num: Int) : Day<List<String>>(num) {
    override fun parseInput(): List<String> = lines
}

val days = listOf(Day01, Day02, Day03, Day04, Day05, Day06, Day07, Day08, Day09, Day10, Day11, Day12)

fun main(args: Array<String>) {
    require(args.isNotEmpty()) { "Day number required" }
    val dayNumber = args[0].toInt()
    val (part1, part2) = days.getOrNull(dayNumber - 1)?.run() ?: throw IllegalArgumentException("Invalid day number")
    println("Day $dayNumber")
    println("Part 1: $part1")
    println("Part 2: $part2")
}