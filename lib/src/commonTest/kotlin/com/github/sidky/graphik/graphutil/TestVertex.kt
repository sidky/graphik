package com.github.sidky.graphik.graphutil

import com.github.sidky.graphik.data.Vertex

data class TestVertex(val id: Int): Vertex

fun Int.toTestVertex(): TestVertex = TestVertex(this)


fun vertices(count: Int) = (0 until count).map { it.toTestVertex() }