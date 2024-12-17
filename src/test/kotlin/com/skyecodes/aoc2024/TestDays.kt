package com.skyecodes.aoc2024

import kotlin.test.Test
import kotlin.test.assertEquals

class TestDays {
    private val examples = mapOf(
        Day01 to Day.Result(11, 31),
        Day02 to Day.Result(2, 4),
        Day03 to Day.Result(161, 48),
        Day04 to Day.Result(18, 9),
        Day05 to Day.Result(143, 123),
        Day06 to Day.Result(41, 6),
        Day07 to Day.Result(3749L, 11387L),
        Day08 to Day.Result(14, 34),
        Day09 to Day.Result(1928L, 2858L),
        Day10 to Day.Result(36, 81),
        Day11 to Day.Result(55312, 65601038650482),
        Day12 to Day.Result(1930, 1206),
        Day13 to Day.Result(480L, 875318608908L),
        Day14 to Day.Result(12L, 0),
        Day15 to Day.Result(10092, 9021),
        Day16 to Day.Result(0, 0),
        Day17 to Day.Result("5,7,3,0", 117440),
        Day18 to Day.Result(22, "6,1"),
    )

    @Test
    fun testAll() {
        examples.forEachAsync { (day, expected) ->
            day.runTest().let { actual ->
                assertEquals(expected.part1, actual.part1, "Day ${day.number} part 1 is incorrect")
                assertEquals(expected.part2, actual.part2, "Day ${day.number} part 2 is incorrect")
            }
        }
    }
}