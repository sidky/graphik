package com.github.sidky.graphik.graphutil

import com.github.sidky.graphik.data.Edge

data class TestEdge(override val from: TestVertex, override val to: TestVertex): Edge

fun Pair<TestVertex, TestVertex>.edge() = TestEdge(this.first, this.second)

