package com.skyecodes.aoc2024

abstract class Day<T : Any>(private val num: Int, private val example: Boolean = false) {
    protected fun readLines(): List<String> = this::class.java.getResourceAsStream("/${if (example) "example" else "day"}%02d.txt".format(num))!!.bufferedReader().readLines()
    protected abstract fun solvePart1(input: T): Any
    protected abstract fun solvePart2(input: T): Any

    protected abstract fun parseInput(): T

    fun run() {
        val input = parseInput()
        println("Part 1: " + solvePart1(input))
        println("Part 2: " + solvePart2(input))
    }
}

abstract class SimpleDay(num: Int, example: Boolean = false) : Day<List<String>>(num, example) {
    override fun parseInput(): List<String> = readLines()
}

fun main(args: Array<String>) {
    require(args.isNotEmpty()) { "Day number required" }
    when (args[0].toInt()) {
        1 -> Day01
        2 -> Day02
        3 -> Day03
        4 -> Day04
        5 -> Day05
        else -> throw IllegalArgumentException("Invalid day number")
    }.run()
}