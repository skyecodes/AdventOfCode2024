package com.skyecodes.aoc2024

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger

fun <E> List<E>.removeAt(index: Int): List<E> = buildList(size - 1) {
    this@removeAt.forEachIndexed { i, e ->
        if (i != index) add(e)
    }
}

fun Iterable<String>.toInt(): List<Int> = map { it.toInt() }

fun Iterable<String>.split(separator: String): List<List<String>> = map { it.split(separator) }

fun Iterable<String>.splitToInt(separator: String): List<List<Int>> = map { it.split(separator).toInt() }

fun <T> Iterable<T>.countAsync(predicate: (T) -> Boolean): Int = AtomicInteger().apply {
    runBlocking(Dispatchers.Default) { forEach { launch { if (predicate(it)) incrementAndGet() } } }
}.toInt()

fun <T> Iterable<T>.filterAsync(predicate: (T) -> Boolean): List<T> = mapAsync { it to predicate(it) }.filter { it.second }.map { it.first }

fun <T, E> Iterable<T>.mapAsync(mapper: (T) -> E): List<E> = runBlocking(Dispatchers.Default) { map { async { mapper(it) } }.awaitAll() }

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun times(scalar: Int) = Point(x * scalar, y * scalar)
}

enum class Direction(dx: Int, dy: Int) {
    TOP_LEFT(-1, -1),
    TOP_CENTER(0, -1),
    TOP_RIGHT(1, -1),
    CENTER_RIGHT(1, 0),
    BOTTOM_RIGHT(1, 1),
    BOTTOM_CENTER(0, 1),
    BOTTOM_LEFT(-1, 1),
    CENTER_LEFT(-1, 0);

    val offset = Point(dx, dy)

    companion object {
        val diagonals = listOf(TOP_LEFT, TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT)
    }
}