package com.github.sidky.graphik.data

class Graph(val vertices: List<Vertex>, val edges: List<Edge>) {

    private val outgoing: Map<Vertex, List<Edge>>
    private val incoming: Map<Vertex, List<Edge>>

    init {
        val inEdge = mutableMapOf<Vertex, MutableList<Edge>>()
        val outEdge = mutableMapOf<Vertex, MutableList<Edge>>()

        for (e in edges) {
            inEdge.getOrPut(e.to) { mutableListOf() }.add(e)
            outEdge.getOrPut(e.from) { mutableListOf() }.add(e)
        }

        incoming = inEdge
        outgoing = outEdge
    }

    fun incoming(vertex: Vertex): List<Edge> = incoming[vertex] ?: emptyList()
    fun outgoing(vertex: Vertex): List<Edge> = outgoing[vertex] ?: emptyList()
}