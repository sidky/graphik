package com.github.sidky.graphik.cycleremover

import com.github.sidky.graphik.data.Graph
import com.github.sidky.graphik.data.Vertex

class CycleTester(private val graph: Graph) {

    val state = mutableMapOf<Vertex, State>()

    val hasCycle by lazy {
        graph.vertices.any { checkCycle(it) }
    }

    init {
        graph.vertices.forEach { state.put(it, State.UNVISITED) }
    }


    private fun checkCycle(u: Vertex): Boolean {
        val s = state[u] ?: State.UNVISITED

        when (s) {
            State.VISITING -> return true
            State.VISITED -> return false
            State.UNVISITED -> {
                state[u] = State.VISITING
                graph.outgoing(u).forEach {
                    if (checkCycle(it.to)) return true
                }
                state[u] = State.VISITED
                return false
            }
        }
    }

    companion object {
        enum class State {
            UNVISITED, VISITING, VISITED
        }
    }
}