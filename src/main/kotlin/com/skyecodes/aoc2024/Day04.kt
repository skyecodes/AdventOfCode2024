package com.skyecodes.aoc2024

object Day04 : SimpleDay(4) {
    private const val WORD = "XMAS"
    override fun solvePart1(input: List<String>): Number =
        input.sumOfUsingPos { pos ->
            Direction.All.count { dir ->
                WORD.indices.all { charIndex ->
                    val offsetPos = pos + dir * charIndex
                    input.getOrNull(offsetPos.y)?.getOrNull(offsetPos.x) == WORD[charIndex]
                }
            }
        }

    private const val DIAGONAL_LETTERS = "MMSS"
    override fun solvePart2(input: List<String>): Number =
        input.sumOfUsingPos { pos ->
            if (input[pos.y][pos.x] == 'A') {
                Direction.Diagonals.indices.count { diagIndex ->
                    DIAGONAL_LETTERS.indices.all { charIndex ->
                        val offsetPos = pos + Direction.Diagonals[(charIndex + diagIndex) % 4]
                        input.getOrNull(offsetPos.y)?.getOrNull(offsetPos.x) == DIAGONAL_LETTERS[charIndex]
                    }
                }
            } else 0
        }

    private fun List<String>.sumOfUsingPos(selector: (Point) -> Int) = indices.sumOf { y ->
        this[y].indices.sumOf { x ->
            selector(Point(x, y))
        }
    }
}