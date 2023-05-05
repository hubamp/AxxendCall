package com.example.axxendvideocall.make_call.presentation.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode


val Green111 = Color(0xFF3BAA4D)
val Green001 = Color(0xFFE8FFEB)
val Green002 = Color(0xFFAFF2B9)
val Breen111 = Color(0xFFA4AA00)
val Breen001 = Color(0xFFFAFBED)
val Breen002 = Color(0xFFFEFFE2)
val Breen003 = Color(0xFFE8EBA8)
val Red111 = Color(0xFFB85959)
val Red001 = Color(0xFFFFF8F8)
val Inactive = Color(0xFFE5E6E5)
val Dull = Color(0xFFD4DAD5)
val Dull3 = Color(0xFF778078)

val backgroundGradient = object : ShaderBrush() {
    override fun createShader(size: Size): Shader {
        val biggerDimension = maxOf(size.height, size.width)
        return LinearGradientShader(
            colors = listOf(Green002, Breen003),
            from = Offset(x = size.width, y = size.height),
            to = Offset(x = 0f, y = 0f),
            colorStops = listOf(0f, 0.5f),
            tileMode = TileMode.Mirror
        )
    }
}


val inactiveBackgroundGradient = object : ShaderBrush() {
    override fun createShader(size: Size): Shader {
        val biggerDimension = maxOf(size.height, size.width)
        return LinearGradientShader(
            colors = listOf(Green001, Dull),
            from = Offset(x = size.width, y = size.height),
            to = Offset(x = 0f, y = 0f),
            colorStops = listOf(0f, 0.5f),
            tileMode = TileMode.Mirror
        )
    }
}

val inactiveBackgroundGradient0 = object : ShaderBrush() {
    override fun createShader(size: Size): Shader {
        val biggerDimension = maxOf(size.height, size.width)
        return LinearGradientShader(
            colors = listOf(Green001, Inactive),
            from = Offset(x = size.width, y = size.height),
            to = Offset(x = 0f, y = 0f),
            colorStops = listOf(0f, 0.55f)
        )
    }
}

val backgroundGradient0 = object : ShaderBrush() {
    override fun createShader(size: Size): Shader {
        val biggerDimension = maxOf(size.height, size.width)
        return LinearGradientShader(
            colors = listOf(Breen001, Green001),
            from = Offset(x = size.width, y = size.height),
            to = Offset(x = 0f, y = 0f),
            colorStops = listOf(0f, 0.55f)
        )
    }
}