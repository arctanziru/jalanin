package com.example.goalsgetter.ui.components

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
import com.example.goalsgetter.ui.theme.Primary
import com.example.goalsgetter.ui.theme.White

enum class ButtonType {
    CONTAINED,
    OUTLINED
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color = Primary,

    textColor: Color = White,
    text: String,

    buttonType: ButtonType = ButtonType.CONTAINED,

    icon: Int? = null,
    enabled: Boolean = true
) {
    when (buttonType) {
        ButtonType.CONTAINED -> {
            Button(
                onClick = onClick,
                modifier = modifier,
                colors = ButtonDefaults.buttonColors(containerColor = color),
                shape = RoundedCornerShape(32.dp),
                enabled = enabled
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
                shape = RoundedCornerShape(32.dp),
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

