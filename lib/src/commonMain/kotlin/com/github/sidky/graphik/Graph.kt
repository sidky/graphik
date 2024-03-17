package com.github.sidky.graphik

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp

@Composable
fun Graph(modifier: Modifier) {
    Box(modifier = Modifier) {

        Text("Text 1", modifier = Modifier.offset(x = 10.0.dp, y = 10.0.dp))
        Text("Text 2", modifier = Modifier.offset(x = 50.0.dp, y = 50.0.dp))
    }
}