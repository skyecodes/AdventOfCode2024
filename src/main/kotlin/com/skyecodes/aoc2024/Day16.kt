package com.skyecodes.aoc2024

import java.util.*

object Day16 : Day<Matrix<Char>>(16) {
    override fun parseInput(): Matrix<Char> = lines.toCharMatrix()

    override fun solvePart1(input: Matrix<Char>): Number {
        val start = input.findAll('S')[0]
        val (_, prev) = dijkstra(input, start)
        val s = mutableListOf<Point>()
        var u: Point? = input.findAll('E')[0]
        if (prev[u] != null || u == start) {
            while (u != null) {
                s.add(0, u)
                u = prev[u]
            }
        }
        return 0
    }

    private fun dijkstra(graph: Matrix<Char>, source: Point): Pair<Map<Point, Int>, Map<Point, Point?>> {
        val q = PriorityQueue<Pair<Point, Int>> { a, b -> a.second.compareTo(b.second) }
        val prev = mutableMapOf<Point, Point?>()
        val dist = mutableMapOf(source to 0)
        q += source to 0

        (graph.findAll('.') + graph.findAll('E')).forEach {
            prev[it] = null
            dist[it] = Int.MAX_VALUE
            q += it to Int.MAX_VALUE
        }

        while (q.isNotEmpty()) {
            val u = q.remove().first
            u.directSurroundings.filter { graph[it] != '#' }.forEach { v ->
                val alt = dist.getValue(u) + 1
                if (alt < dist.getValue(v)) {
                    prev[v] = u
                    dist[v] = alt
                    q.removeIf { (p, _) -> p == v }
                    q += v to alt
                }
            }
        }

        return dist to prev
    }

    /*private fun visit(
        matrix: Matrix<Char>,
        pos: Point,
        previousDirection: Point? = null,
        history: Set<Point> = emptySet(),
        score: Int = 0
    ): Int {
        if (matrix[pos] == 'E') return score
        return Direction.Direct.map { it to pos + it }
            .filter { (_, nextPos) -> matrix[nextPos] != '#' && nextPos !in history }
            .mapAsync { (direction, nextPos) ->
                var nextScore = score
                if (direction != previousDirection) nextScore += 1000
                visit(matrix, nextPos, direction, history + pos, nextScore + 1)
            }.minOrNull() ?: Int.MAX_VALUE
    }*/

    override fun solvePart2(input: Matrix<Char>): Number {
        return 0
    }
}