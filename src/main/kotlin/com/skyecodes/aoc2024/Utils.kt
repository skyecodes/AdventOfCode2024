package com.skyecodes.aoc2024

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.abs

fun <E> List<E>.removeAt(index: Int): List<E> = buildList(size - 1) {
    this@removeAt.forEachIndexed { i, e ->
        if (i != index) add(e)
    }
}

fun Iterable<String>.toInt(): List<Int> = map { it.toInt() }

fun Iterable<String>.toLong(): List<Long> = map { it.toLong() }

fun Iterable<String>.split(separator: String): Matrix<String> = map { it.split(separator) }

fun Iterable<String>.splitToInt(separator: String): Matrix<Int> = map { it.split(separator).toInt() }

fun Iterable<String>.toCharMatrix(): Matrix<Char> = map { it.map { it } }

fun <T> Iterable<T>.countAsync(predicate: (T) -> Boolean): Int = AtomicInteger().apply {
    runBlocking(Dispatchers.Default) { forEach { launch { if (predicate(it)) incrementAndGet() } } }
}.get()

fun <T> Iterable<T>.filterAsync(predicate: (T) -> Boolean): List<T> = mapAsync { it to predicate(it) }.filter { it.second }.map { it.first }

fun <T> Iterable<T>.forEachAsync(action: (T) -> Unit) =
    runBlocking(Dispatchers.Default) { map { launch { action(it) } }.joinAll() }

fun <K, V> Map<out K, V>.forEachAsync(action: (Map.Entry<K, V>) -> Unit) =
    runBlocking(Dispatchers.Default) { map { launch { action(it) } }.joinAll() }

fun <T, E> Iterable<T>.mapAsync(mapper: (T) -> E): List<E> = runBlocking(Dispatchers.Default) { map { async { mapper(it) } }.awaitAll() }

fun <T> generateCombinations(values: List<T>, times: Int): List<List<T>> =
    generateCombinations((0 until times).map { values })

fun <T> generateCombinations(lists: List<List<T>>): List<List<T>> =
    lists.fold(listOf(listOf<T>())) { acc, set -> acc.flatMap { list -> set.map { element -> list + element } } }

fun <T : Any> T.repeatMap(times: Int, action: T.(Int) -> T): T {
    var t = this
    repeat(times) {
        t = t.action(it)
    }
    return t
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    operator fun div(other: Point) = Point(x / other.x, y / other.y)
    operator fun rem(other: Point) = Point(x % other.x, y % other.y)
    operator fun plus(scalar: Int) = Point(x + scalar, y + scalar)
    operator fun times(scalar: Int) = Point(x * scalar, y * scalar)
    operator fun div(scalar: Int) = Point(x / scalar, y / scalar)
    operator fun unaryMinus() = Point(-x, -y)
    fun withinBounds(end: Point, start: Point = Zero) = x >= start.x && y >= start.y && x < end.x && y < end.y
    fun withinBoundsInclusive(end: Point, start: Point = Zero) =
        x >= start.x && y >= start.y && x <= end.x && y <= end.y
    fun keepWithin(area: Point) = (this % area).let { (x, y) ->
        Point(if (x < 0) area.x + x else x, if (y < 0) area.y + y else y)
    }

    val directSurroundings by lazy { Direction.Direct.map { this + it } }

    companion object {
        val Zero = Point(0, 0)
    }
}

object Direction {
    val TopLeft = Point(-1, -1)
    val Top = Point(0, -1)
    val TopRight = Point(1, -1)
    val Right = Point(1, 0)
    val BottomRight = Point(1, 1)
    val Bottom = Point(0, 1)
    val BottomLeft = Point(-1, 1)
    val Left = Point(-1, 0)

    val All = listOf(TopLeft, Top, TopRight, Right, BottomRight, Bottom, BottomLeft, Left)
    val Direct = listOf(Top, Right, Bottom, Left)
    val Diagonals = listOf(TopLeft, TopRight, BottomRight, BottomLeft)
}

data class PointWithDirection(
    val point: Point,
    val direction: Point
)

fun manhattan(a: Point, b: Point) = abs(a.x - b.x) + abs(a.y - b.y)

typealias Matrix<T> = List<List<T>>

operator fun <T> Matrix<T>.get(x: Int, y: Int): T? = getOrNull(y)?.getOrNull(x)

operator fun <T> Matrix<T>.get(p: Point): T? = get(p.x, p.y)

fun <T> Matrix<T>.findAll(value: T): List<Point> = flatMapIndexed { y, line ->
    line.flatMapIndexed { x, v ->
        if (v == value) listOf(Point(x, y)) else emptyList()
    }
}

fun <T> Matrix<T>.forEachPoint(action: (Point, T) -> Unit) = forEachIndexed { y, line ->
    line.forEachIndexed { x, v ->
        action(Point(x, y), v)
    }
}

operator fun <T> List<T>.component6() = get(5)