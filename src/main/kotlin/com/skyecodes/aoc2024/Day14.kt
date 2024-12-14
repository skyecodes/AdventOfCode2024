package com.skyecodes.aoc2024

object Day14 : Day<List<Day14.Robot>>(14) {
    val area by lazy { if (isTest) Point(11, 7) else Point(101, 103) }
    val middle by lazy { area / 2 }

    override fun parseInput(): List<Robot> = lines.map { line ->
        line.split(" ").map { part ->
            part.substring(2).split(",").map { it.toInt() }.let { (x, y) -> Point(x, y) }
        }.let { (p, v) -> Robot(p, v) }
    }

    override fun solvePart1(input: List<Robot>): Number {
        var robots = input
        repeat(100) {
            robots = robots.map { it.move() }
        }
        return robots.filter { it.quadrant != null }.groupingBy { it.quadrant }
            .eachCount().values.fold(1L) { acc, n -> acc * n }
    }

    override fun solvePart2(input: List<Robot>): Number {
        if (isTest) return 0
        val detectionArea = Point(3, 3)
        var robots = input
        var time = 1
        while (true) {
            robots = robots.map { it.move() }
            val points = robots.map { it.pos }.distinct()
            if (points.any { (x, y) ->
                    (0..<detectionArea.x).all { dx ->
                        (0..<detectionArea.y).all { dy ->
                            Point(x + dx, y + dy) in points
                        }
                    }
                }) return time.also { robots.print() }
            time++
        }
    }

    data class Robot(val pos: Point, val velocity: Point) {
        fun move() = Robot((pos + velocity).keepWithin(area), velocity)
        val quadrant: Point? = run {
            if (pos.x == middle.x || pos.y == middle.y) null
            else Point(if (pos.x < middle.x) 0 else 1, if (pos.y < middle.y) 0 else 1)
        }
    }

    private fun List<Robot>.print(quadrants: Boolean = false) {
        val count = groupingBy { it.pos }.eachCount()
        (0..<area.y).forEach { y ->
            if (!quadrants || y != middle.y) {
                (0..<area.x).forEach { x ->
                    if (!quadrants || x != middle.x) {
                        val pos = Point(x, y)
                        print((count[pos] ?: '.').toString() + ' ')
                    } else {
                        print("  ")
                    }
                }
            }
            println()
        }
    }
}