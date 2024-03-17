package com.github.sidky.graphik.layerassignment

import com.github.sidky.graphik.algorithm.layerassignment.TopologicalSortLayerAssignment
import com.github.sidky.graphik.data.Graph
import com.github.sidky.graphik.graphutil.edge
import com.github.sidky.graphik.graphutil.vertices
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TopologicalSortLayerAssignmentTest {

    @Test
    fun testLineGraph() {
        val vertices = vertices(5)
        val edges = listOf(
            (vertices[0] to vertices[1]).edge(),
            (vertices[1] to vertices[2]).edge(),
            (vertices[2] to vertices[4]).edge(),
            (vertices[4] to vertices[3]).edge(),
        )

        val graph = Graph(vertices, edges)

        val assignment = TopologicalSortLayerAssignment(graph)

        assertEquals(5, assignment.numberOfLayers())
        assertEquals(0, assignment.layerNumber(vertices[0]))
        assertEquals(1, assignment.layerNumber(vertices[1]))
        assertEquals(2, assignment.layerNumber(vertices[2]))
        assertEquals(4, assignment.layerNumber(vertices[3]))
        assertEquals(3, assignment.layerNumber(vertices[4]))
        assertTrue {
            vertices.all { assignment.layerNumber(it)
                .let {
                    it >= 0 && it < assignment.numberOfLayers()
                }
            }
        }
    }

    @Test
    fun testDag() {
        val vertices = vertices(5)
        val edges = listOf(
            (vertices[0] to vertices[1]).edge(),
            (vertices[1] to vertices[2]).edge(),
            (vertices[1] to vertices[3]).edge(),
            (vertices[2] to vertices[4]).edge(),
            (vertices[3] to vertices[4]).edge(),
        )
        val graph = Graph(vertices, edges)

        val assignment = TopologicalSortLayerAssignment(graph)

        assertTrue {
            assignment.layerNumber(vertices[0]) < assignment.layerNumber(vertices[1])
        }

        assertTrue {
            assignment.layerNumber(vertices[1]) < minOf(assignment.layerNumber(vertices[2]), assignment.layerNumber(vertices[3]))
        }

        assertTrue {
            assignment.layerNumber(vertices[2]) < assignment.layerNumber(vertices[4])
        }

        assertTrue {
            assignment.layerNumber(vertices[3]) < assignment.layerNumber(vertices[4])
        }

        assertTrue {
            vertices.all { assignment.layerNumber(it)
                .let {
                    it >= 0 && it < assignment.numberOfLayers()
                }
            }
        }
    }

    @Test
    fun testMultipleNodeWithoutInDegree() {
        val vertices = vertices(5)
        val edges = listOf(
            (vertices[0] to vertices[1]).edge(),
            (vertices[1] to vertices[2]).edge(),
            (vertices[1] to vertices[3]).edge(),
            (vertices[4] to vertices[1]).edge(),
        )
        val graph = Graph(vertices, edges)

        val assignment = TopologicalSortLayerAssignment(graph)
        assertEquals(3, assignment.numberOfLayers())

        edges.forEach {
            assertTrue {
                assignment.layerNumber(it.from) < assignment.layerNumber(it.to)
            }
        }

        assertTrue {
            vertices.all { assignment.layerNumber(it)
                .let {
                    it >= 0 && it < assignment.numberOfLayers()
                }
            }
        }
    }

    @Test
    fun testDisjointGraph() {
        val vertices = vertices(5)
        val edges = listOf(
            (vertices[0] to vertices[2]).edge(),
            (vertices[4] to vertices[1]).edge(),
        )
        val graph = Graph(vertices, edges)

        val assignment = TopologicalSortLayerAssignment(graph)

        assertEquals(2, assignment.numberOfLayers())

        for (e in edges) {
            assertTrue {
                assignment.layerNumber(e.from) < assignment.layerNumber(e.to)
            }
        }

        assertTrue {
            vertices.all { assignment.layerNumber(it)
                .let {
                    it >= 0 && it < assignment.numberOfLayers()
                }
            }
        }
    }
}