package com.example.jalanin.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ButtonType {
    CONTAINED,
    OUTLINED
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier.padding(14.dp, 8.dp),
    onClick: () -> Unit,
    color: Color,

    textColor: Color,
    text: String,

    buttonType: ButtonType,

    icon: Int? = null,
    enabled: Boolean = false
) {
    when (buttonType) {
        ButtonType.CONTAINED -> {
            Button(
                onClick = onClick,
                modifier = modifier,
                colors = ButtonDefaults.buttonColors(containerColor = color),
                shape = RoundedCornerShape(8.dp),
                enabled = enabled
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    icon?.let {
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = it),
                            contentDescription = null,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                    }
                    Text(
                        text = text,
                        color = textColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            }
        }
        ButtonType.OUTLINED -> {
            Button(
                onClick = onClick,
                modifier = modifier,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = color),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, color),
                enabled = enabled,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    icon?.let {
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = it),
                            contentDescription = null,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                    }
                    Text(
                        text = text,
                        color = color,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

