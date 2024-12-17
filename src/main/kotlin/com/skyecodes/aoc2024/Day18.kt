package com.skyecodes.aoc2024

import java.util.*

object Day18 : Day<List<Point>>(18) {
    val goal by lazy { if (isTest) Point(6, 6) else Point(70, 70) }
    val qty1 by lazy { if (isTest) 12 else 1024 }

    override fun parseInput(): List<Point> =
        lines.map { it.split(',').map { it.toInt() }.let { (x, y) -> Point(x, y) } }

    override fun solvePart1(input: List<Point>): Int = findPath(input, qty1).size - 1

    override fun solvePart2(input: List<Point>): String {
        var s = emptyList<Point>()
        return input.indices.first {
            if (s.isEmpty() || input[it] in s) {
                s = findPath(input, it + 1)
            }
            Point.Zero !in s
        }.let { input[it].let { (x, y) -> "$x,$y" } }
    }

    private fun findPath(input: List<Point>, bytes: Int): List<Point> {
        val kb = input.take(bytes)
        val map = (0..goal.y).map { y ->
            (0..goal.x).map { x ->
                Point(x, y) !in kb
            }
        }
        val start = Point.Zero
        val prev = dijkstra(map, start)
        val s = mutableListOf<Point>()
        var u: Point? = goal
        if (prev[u] != null || u == start) {
            while (u != null && u !in s) {
                s.add(0, u)
                u = prev[u]
            }
        }
        return s
    }

    private fun dijkstra(graph: Matrix<Boolean>, source: Point): Map<Point, Point?> {
        val q = PriorityQueue<Pair<Point, Int>> { a, b -> a.second.compareTo(b.second) }
        val prev = mutableMapOf<Point, Point?>()
        val dist = mutableMapOf(source to 0)
        q += source to 0

        (graph.findAll(true) - source).forEach {
            prev[it] = null
            dist[it] = Int.MAX_VALUE
            q += it to Int.MAX_VALUE
        }

        while (q.isNotEmpty()) {
            val u = q.remove().first
            u.directSurroundings.filter { graph[it] == true }.forEach { v ->
                val alt = dist.getValue(u) + 1
                if (alt < dist.getValue(v)) {
                    prev[v] = u
                    dist[v] = alt
                    q.removeIf { (p, _) -> p == v }
                    q += v to alt
                }
            }
        }

        return prev
    }

    /*private fun astar(graph: Matrix<Boolean>, start: Point): List<Point> {
        val h: (Point) -> Int = { manhattan(it, goal) }
        val cameFrom = mutableMapOf<Point, Point>()
        val gScore = mutableMapOf<Point, Int>(start to 0)
        val fScore = mutableMapOf<Point, Int>(start to h(start))
        val openSet = PriorityQueue<Point> { a, b ->

        }
        while (openSet.isNotEmpty()) {
            val current = openSet.minOf { fScore.getOrDefault(it, Int.MAX_VALUE) }
        }
    }*/
}