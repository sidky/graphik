package com.github.sidky.graphik.algorithm.cycleremover

import com.github.sidky.graphik.data.Graph

abstract class CycleRemover(val graph: Graph) {

    abstract fun removeCycles(): Graph
}