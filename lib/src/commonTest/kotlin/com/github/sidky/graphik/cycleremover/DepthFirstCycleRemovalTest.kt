package com.github.sidky.graphik.cycleremover

import com.github.sidky.graphik.algorithm.cycleremover.DepthFirstCycleRemoval
import com.github.sidky.graphik.data.Graph
import com.github.sidky.graphik.graphutil.edge
import com.github.sidky.graphik.graphutil.toTestVertex
import com.github.sidky.graphik.graphutil.vertices
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DepthFirstCycleRemovalTest {
    
    @Test
    fun testRemoval() {
        val vertices = vertices(4)
        val edges = listOf(
            (vertices[0] to vertices[1]).edge(),
            (vertices[1] to vertices[2]).edge(),
            (vertices[2] to vertices[0]).edge(),
            (vertices[2] to vertices[3]).edge(),
            (vertices[3] to vertices[0]).edge(),
        )
        val graph = Graph(vertices, edges)

        val remover = DepthFirstCycleRemoval(graph)
        val newGraph = remover.removeCycles()

        assertEquals(graph.vertices.toSet(), newGraph.vertices.toSet())
        assertTrue { graph.edges.toSet().containsAll(newGraph.edges.toSet()) }
        assertEquals(3, newGraph.edges.size)
        assertFalse(CycleTester(newGraph).hasCycle)
    }

    @Test
    fun testRemovalNoCycle() {
        val vertices = vertices(5)
        val edges = listOf(
            (vertices[0] to vertices[1]).edge(),
            (vertices[1] to vertices[2]).edge(),
            (vertices[1] to vertices[3]).edge(),
            (vertices[2] to vertices[4]).edge(),
            (vertices[3] to vertices[4]).edge(),
        )
        val graph = Graph(vertices, edges)

        val remover = DepthFirstCycleRemoval(graph)
        val newGraph = remover.removeCycles()

        assertEquals(graph.vertices.toSet(), newGraph.vertices.toSet())
        assertEquals(graph.edges.toSet(), newGraph.edges.toSet())
        assertFalse(CycleTester(newGraph).hasCycle)
    }

    @Test
    fun testDisjointedGraph() {
        val vertices = vertices(5)
        val graph = Graph(vertices, emptyList())

        val remover = DepthFirstCycleRemoval(graph)
        val newGraph = remover.removeCycles()

        assertEquals(graph.vertices.toSet(), newGraph.vertices.toSet())
        assertTrue { newGraph.edges.isEmpty() }
    }
}