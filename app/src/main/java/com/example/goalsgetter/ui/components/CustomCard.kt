package com.example.goalsgetter.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.goalsgetter.ui.theme.White

enum class PaddingMode {
    ALL,
    VERTICAL
}


@Composable
fun CustomCard(
    containerColor: Color = White,
    paddingMode: PaddingMode = PaddingMode.ALL,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = if (paddingMode == PaddingMode.ALL) 24.dp else 0.dp)
        ) {
            content()
        }
    }
}
