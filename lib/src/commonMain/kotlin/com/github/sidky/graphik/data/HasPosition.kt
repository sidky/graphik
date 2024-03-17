package com.github.sidky.graphik.data

interface HasPosition {
    val position: Position
}

class WithPosition(override val position: Position) : HasPosition