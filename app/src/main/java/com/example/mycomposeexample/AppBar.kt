package com.example.mycomposeexample

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AppBar(title: String, icon: ImageVector, iconClickListener: () -> Unit = {}) {
    TopAppBar(
        navigationIcon = {
            Icon(icon,
                contentDescription = "title",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { iconClickListener() })
         },
        title = {
            Text(title)
        }
    )
}