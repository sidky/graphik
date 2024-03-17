package com.github.sidky.graphik.algorithm.cycleremover

import androidx.compose.ui.Modifier
import com.github.sidky.graphik.data.Edge
import com.github.sidky.graphik.data.Graph
import com.github.sidky.graphik.data.Vertex

class DepthFirstCycleRemoval(graph: Graph): CycleRemover(graph) {

    private val selectedEdges = mutableListOf<Edge>()
    private val nodeState = mutableMapOf<Vertex, NodeState>()

    override fun removeCycles(): Graph {
        selectedEdges.clear()
        nodeState.clear()

        for (v in graph.vertices) {
            val state = nodeState.getOrElse(v) { NodeState.UNVISITED }

            if (state == NodeState.UNVISITED) {
                dfs(v)
            }
        }

        return Graph(vertices = graph.vertices, edges = selectedEdges)
    }

    private fun dfs(vertex: Vertex) {
        nodeState[vertex] = NodeState.PROCESSING

        for (edge in graph.outgoing(vertex)) {
            val state = nodeState[edge.to] ?: NodeState.UNVISITED

            when (state) {
                NodeState.UNVISITED -> {
                    selectedEdges.add(edge)
                    dfs(edge.to)
                }
                NodeState.PROCESSING -> {
                    // a cycle has been found. The edge will not be selected.
                }
                NodeState.PROCESSED -> {
                    // a forward edge has been found. The edge will be kept.
                    selectedEdges.add(edge)
                }
            }
        }

        nodeState[vertex] = NodeState.PROCESSED
    }

    private enum class NodeState {
        UNVISITED,
        PROCESSING,
        PROCESSED,
    }
}