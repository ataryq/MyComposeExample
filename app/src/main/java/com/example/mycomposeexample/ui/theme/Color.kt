package com.example.mycomposeexample.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val VeryLightGray = Color(0xFFF0F0F0)
val LightGreen = Color(0xFF44AD49)
val LightRed = Color(0xFFFF5252)

val Colors.lightGreen: Color
    @Composable
    get() = LightGreen

val Colors.lightRed: Color
    @Composable
    get() = LightRed