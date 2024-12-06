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
        val maxX = lines.maxOf { it.length }
        val maxY = lines.size
        return Input(walls, start!!, maxX, maxY)
    }

    val directions =
        listOf(Direction.TOP_CENTER, Direction.CENTER_RIGHT, Direction.BOTTOM_CENTER, Direction.CENTER_LEFT)
    private val part1cells = mutableSetOf<Point>()

    override fun solvePart1(input: Input): Any {
        var currentDirIndex = 0
        var currentPos = input.start
        while (currentPos.x in 0..input.maxX - 1 && currentPos.y in 0..input.maxY - 1) {
            part1cells += currentPos
            val newPos = currentPos + directions[currentDirIndex].offset
            if (newPos in input.walls) {
                currentDirIndex = (currentDirIndex + 1) % directions.size
            } else {
                currentPos = newPos
            }
        }
        return part1cells.size
    }

    override fun solvePart2(input: Input): Any = part1cells.filter { it != input.start  }.countAsync { (x, y) ->
        val walls = input.walls + Point(x, y)
        val cells = mutableSetOf<PointWithDirection>()
        var currentDirIndex = 0
        var currentPos = input.start
        while (currentPos.x in 0..input.maxX - 1 && currentPos.y in 0..input.maxY - 1) {
            val pointWithDirection = PointWithDirection(currentPos, directions[currentDirIndex])
            if (pointWithDirection in cells) return@countAsync true
            cells += pointWithDirection
            val newPos = currentPos + directions[currentDirIndex].offset
            if (newPos in walls) {
                currentDirIndex = (currentDirIndex + 1) % directions.size
            } else {
                currentPos = newPos
            }
        }
        false
    }

    data class Input(
        val walls: List<Point>,
        val start: Point,
        val maxX: Int,
        val maxY: Int
    )

    data class PointWithDirection(
        val point: Point,
        val direction: Direction
    )
}