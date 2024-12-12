package com.skyecodes.aoc2024

object Day12 : Day<Input>(12) {
    override fun parseInput(): Input = buildList {
        lines.map { it.map { it } }.let {
            it.forEachPoint { pos, c ->
                if (pos !in flatten()) add(scanRegion(it, pos, c))
            }
        }
    }

    override fun solvePart1(input: Input): Number =
        input.sumOf { it.size * it.perimeter }

    override fun solvePart2(input: Input): Number =
        input.sumOf { it.size * it.sides }

    private fun scanRegion(
        matrix: Matrix<Char>,
        pos: Point,
        c: Char,
        region: MutableList<Point> = mutableListOf(pos)
    ): Region =
        region.apply {
            pos.directSurroundings.filter { matrix[it] == c }.forEach {
                if (it !in this) {
                    add(it)
                    scanRegion(matrix, it, c, region)
                }
            }
        }

    private val Region.perimeter get() = sumOf { it.directSurroundings.count { it !in this } }

    private val Region.sides
        get() = filter { pos ->
            // Step 1: only take elements of the region that are on a border
            pos.directSurroundings.any { it !in this }
        }.flatMap { pos ->
            // Step 2: associate those elements with the direction(s) in which the border is
            Direction.Direct.associateWith { pos }.filter { (dir, pos) -> pos + dir !in this }.entries
        }.fold(mapOf<Point, List<Point>>()) { acc, (dir, pos) ->
            // Step 3: group those elements by direction, so we have a Map<Direction, List<Point>>
            acc + (dir to (acc[dir]?.plus(pos) ?: listOf(pos)))
        }.entries.sumOf { (dir, posList) ->
            // Step 4: for each of these groups, group those elements by their X coordinate if it's a horizontal border,
            // or Y coordinate if it's a vertical border - and only keep the coordinate of the other axis,
            // so we have a Map<Int, Int> where the key is the coordinate on the axis of the direction of the border, and the other coordinate
            posList.groupBy({ if (dir.x != 0) it.x else it.y }) { if (dir.y != 0) it.x else it.y }.values.sumOf { line ->
                // Step 5: for each of these groups, we take the values and count how many successive groups of coordinates we have
                // Ex: 0, 1, 2, 7, 8, 9 counts as 2 groups => in case we have two borders on the exact same axis
                line.sorted().zipWithNext().count { (a, b) -> a + 1 != b } + 1
            }
        }

}

private typealias Input = List<Region>
private typealias Region = List<Point>