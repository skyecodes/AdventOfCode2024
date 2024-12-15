package com.skyecodes.aoc2024

import com.skyecodes.aoc2024.Day15.Input

object Day15 : Day<Input>(15) {
    enum class ElementType {
        Robot,
        Wall,
        Box
    }

    data class Element(var pos: Point, val type: ElementType)

    data class Input(
        val elements: List<Element>,
        val moves: List<Point>
    )

    override fun parseInput(): Input {
        val elements = lines.takeWhile { it.isNotBlank() }.flatMapIndexed { y, line ->
            line.flatMapIndexed { x, c ->
                val pos = Point(x, y)
                when (c) {
                    '@' -> listOf(Element(pos, ElementType.Robot))
                    '#' -> listOf(Element(pos, ElementType.Wall))
                    'O' -> listOf(Element(pos, ElementType.Box))
                    else -> emptyList()
                }
            }
        }
        val moves = lines.dropWhile { it.isNotBlank() }.joinToString(separator = "").map { c ->
            when (c) {
                '^' -> Direction.Top
                '>' -> Direction.Right
                'v' -> Direction.Bottom
                else -> Direction.Left
            }
        }
        return Input(elements, moves)
    }

    private fun solve(
        elements: List<Element>,
        moves: List<Point>,
        moveAction: (List<Element>, Element, Point) -> Unit
    ): Int {
        val robot = elements.first { it.type == ElementType.Robot }
        moves.forEach { move -> moveAction(elements, robot, move) }
        return elements.filter { it.type == ElementType.Box }.sumOf { 100 * it.pos.y + it.pos.x }
    }

    override fun solvePart1(input: Input): Number = solve(input.elements.map { it.copy() }, input.moves, ::tryMove1)

    private fun tryMove1(elements: List<Element>, element: Element, move: Point): Boolean {
        val nextPos = element.pos + move
        val nextPosElement = elements.find { it.pos == nextPos }
        return when (nextPosElement?.type) {
            ElementType.Wall -> false
            ElementType.Box -> {
                if (tryMove1(elements, nextPosElement, move)) {
                    element.pos = nextPos
                    true
                } else false
            }

            else -> {
                element.pos = nextPos
                true
            }
        }
    }

    override fun solvePart2(input: Input): Number =
        solve(input.elements.map { it.copy(pos = Point(it.pos.x * 2, it.pos.y)) }, input.moves, ::tryMove2)

    private fun tryMove2(elements: List<Element>, element: Element, move: Point, simulated: Boolean = false): Boolean {
        val nextPos = element.pos + move
        val nextPosElements = listOfNotNull(
            elements.find { it.pos == nextPos }
                ?: if (move != Direction.Right) elements.find { it.pos == nextPos + Direction.Left } else null,
            if (element.type != ElementType.Robot && move != Direction.Left) elements.find { it.pos == nextPos + Direction.Right } else null
        )
        return when {
            nextPosElements.any { it.type == ElementType.Wall } -> false
            nextPosElements.isNotEmpty() -> {
                if (nextPosElements.all { tryMove2(elements, it, move, true) }) {
                    if (!simulated) {
                        nextPosElements.forEach { tryMove2(elements, it, move) }
                        element.pos = nextPos
                    }
                    true
                } else false
            }

            else -> {
                if (!simulated) element.pos = nextPos
                true
            }
        }
    }
}