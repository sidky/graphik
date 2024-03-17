package com.github.sidky.graphik.algorithm.layerassignment

import com.github.sidky.graphik.data.Graph
import com.github.sidky.graphik.data.Vertex

interface LayerAssignment {
    val graph: Graph

    fun numberOfLayers(): Int

    fun layerNumber(vertex: Vertex): Int
}