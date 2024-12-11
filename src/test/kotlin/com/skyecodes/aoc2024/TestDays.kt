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
        Day07 to Day.Result(3749, 11387),
        Day08 to Day.Result(14, 34),
        Day09 to Day.Result(1928, 2858),
        Day10 to Day.Result(36, 81),
        Day11 to Day.Result(55312, 65601038650482)
    )

    @Test
    fun testAll() = examples.forEachAsync { (day, expected) ->
        day.run().let { actual ->
            assertEquals(expected.part1.toLong(), actual.part1.toLong(), "Day ${day.number} part 1 is incorrect")
            assertEquals(expected.part2.toLong(), actual.part2.toLong(), "Day ${day.number} part 2 is incorrect")
        }
    }
}