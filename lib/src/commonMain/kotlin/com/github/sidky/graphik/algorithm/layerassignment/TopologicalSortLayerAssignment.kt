package com.github.sidky.graphik.algorithm.layerassignment

import com.github.sidky.graphik.data.Graph
import com.github.sidky.graphik.data.Vertex

class TopologicalSortLayerAssignment(override val graph: Graph) : LayerAssignment {

    private val layerIdInverted = mutableMapOf<Vertex, Int>()
    private var maxLayers = 0

    init {
        for (v in graph.vertices) {
            dfs(v)
        }
    }

    override fun numberOfLayers(): Int = maxLayers

    override fun layerNumber(vertex: Vertex): Int = maxLayers - (layerIdInverted.getOrPut(vertex) { 0 }) - 1

    private fun dfs(vertex: Vertex): Int {
        if (layerIdInverted[vertex] == -1) throw IllegalStateException("Cycle detected")
        layerIdInverted[vertex]?.let {
            return it
        }

        layerIdInverted[vertex] = -1
        var maxChild = -1

        for (e in graph.outgoing(vertex)) {
            val v = e.to

            val childId = dfs(v)

            maxChild = maxOf(maxChild, childId)
        }

        layerIdInverted[vertex] = maxChild + 1

        maxLayers = maxOf(maxLayers, maxChild + 1 + 1)

        return maxChild + 1
    }
}