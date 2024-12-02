package com.skyecodes.aoc2024

fun <E> List<E>.removeAt(index: Int): List<E> = buildList(size - 1) {
    this@removeAt.forEachIndexed { i, e ->
        if (i != index) add(e)
    }
}

fun List<String>.toInt(): List<Int> = map { it.toInt() }

fun List<String>.split(separator: String): List<List<String>> = map { it.split(separator) }

fun List<String>.splitToInt(separator: String): List<List<Int>> = map { it.split(separator).toInt() }