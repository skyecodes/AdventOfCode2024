package com.skyecodes.aoc2024

import com.skyecodes.aoc2024.Day09.Block

object Day09 : Day<List<Block>>(9) {
    override fun parseInput(): List<Block> {
        var isFile = true
        var fileId = 0
        return lines[0].map { c ->
            c.digitToInt().let { if (isFile) Block.File(fileId, it) else Block.Empty(it) }.also {
                if (isFile) fileId++
                isFile = !isFile
            }
        }
    }

    override fun solvePart1(input: List<Block>): Number {
        val list = input.toMutableList()
        while (list.any { it is Block.Empty }) {
            var block = list.removeLast()
            if (block is Block.File) {
                var file: Block.File? = block
                while (file != null) {
                    val spaceIndex = list.indexOfFirst { it is Block.Empty }
                    if (spaceIndex == -1) {
                        list.add(file)
                        file = null
                    } else {
                        val space = list.removeAt(spaceIndex)
                        if (space.size > file.size) {
                            list.add(spaceIndex, Block.Empty(space.size - file.size))
                        }
                        if (space.size >= file.size) {
                            list.add(spaceIndex, file)
                            file = null
                        } else {
                            list.add(spaceIndex, Block.File(file.fileId, space.size))
                            file = Block.File(file.fileId, file.size - space.size)
                        }
                    }
                }
            }
        }
        return getResult(list)
    }

    override fun solvePart2(input: List<Block>): Number {
        val list = input.toMutableList()
        for (index in list.indices.reversed()) {
            val block = list[index]
            if (block is Block.File) {
                val spaceIndex = list.indexOfFirst { it is Block.Empty && it.size >= block.size }
                if (spaceIndex != -1 && spaceIndex < index) {
                    list[index] = Block.Empty(block.size)
                    val space = list.removeAt(spaceIndex)
                    if (space.size > block.size) {
                        list.add(spaceIndex, Block.Empty(space.size - block.size))
                    }
                    list.add(spaceIndex, block)
                }
            }
        }
        return getResult(list)
    }

    private fun getResult(blocks: List<Block>): Long {
        var res = 0L
        var i = 0
        blocks.forEach { block ->
            repeat(block.size) {
                if (block is Block.File) res += (i + it) * block.fileId
            }
            i += block.size
        }
        return res
    }

    sealed interface Block {
        val size: Int

        data class File(val fileId: Int, override val size: Int) : Block
        data class Empty(override val size: Int) : Block
    }
}