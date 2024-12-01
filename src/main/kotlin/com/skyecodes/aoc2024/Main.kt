package com.skyecodes.aoc2024

abstract class Day<T : Any>(private val num: Int) {
    protected fun readLines(): List<String> = this::class.java.getResourceAsStream("/day%02d.txt".format(num))!!.bufferedReader().readLines()
    abstract fun solvePart1(input: T): Any
    abstract fun solvePart2(input: T): Any

    protected abstract fun parseInput(): T

    fun run() {
        val input = parseInput()
        println("Part 1: " + solvePart1(input))
        println("Part 2: " + solvePart2(input))
    }
}

abstract class SimpleDay(num: Int) : Day<List<String>>(num) {
    override fun parseInput(): List<String> = readLines()
}

fun main(args: Array<String>) {
    require(args.isNotEmpty()) { "Day number required" }
    when (args[0].toInt()) {
        1 -> Day01
        else -> throw IllegalArgumentException("Invalid day number")
    }.run()
}