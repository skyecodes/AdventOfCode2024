package com.skyecodes.aoc2024

object Day06 : Day<Day06.Input>(6) {
    override fun parseInput(): Input {
        val lines = readLines()
        var start: Point? = null
        val walls = buildList {
            lines.forEachIndexed { y, line ->
                line.forEachIndexed { x, ch ->
                    when (ch) {
                        '#' -> add(Point(x, y))
                        '^' -> start = Point(x, y)
                    }
                }
            }
        }
        return Input(walls, start!!, Point(lines[0].length, lines.size))
    }

    private val visitedPos = mutableSetOf<Point>()

    override fun solvePart1(input: Input): Any {
        var currentDirIndex = 0
        var currentPos = input.start
        while (currentPos.withinBounds(input.max)) {
            visitedPos += currentPos
            val newPos = currentPos + Direction.Direct[currentDirIndex]
            if (newPos in input.walls) {
                currentDirIndex = (currentDirIndex + 1) % Direction.Direct.size
            } else {
                currentPos = newPos
            }
        }
        return visitedPos.size
    }

    override fun solvePart2(input: Input): Any = visitedPos.filter { it != input.start }.countAsync { (x, y) ->
        val walls = input.walls + Point(x, y)
        val visitedPosWithDir = mutableSetOf<PointWithDirection>()
        var currentDirIndex = 0
        var currentPos = input.start
        while (currentPos.withinBounds(input.max)) {
            val pointWithDirection = PointWithDirection(currentPos, Direction.Direct[currentDirIndex])
            if (pointWithDirection in visitedPosWithDir) return@countAsync true
            visitedPosWithDir += pointWithDirection
            val newPos = currentPos + Direction.Direct[currentDirIndex]
            if (newPos in walls) {
                currentDirIndex = (currentDirIndex + 1) % Direction.Direct.size
            } else {
                currentPos = newPos
            }
        }
        false
    }

    data class Input(
        val walls: List<Point>,
        val start: Point,
        val max: Point
    )

    data class PointWithDirection(
        val point: Point,
        val direction: Point
    )
}