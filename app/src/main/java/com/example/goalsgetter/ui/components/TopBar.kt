package com.example.goalsgetter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.goalsgetter.R

enum class AppBarVariant {
    PLAIN, BUTTON
}

@Composable
fun AppBar(
    title: String,
    variant: AppBarVariant = AppBarVariant.PLAIN,
    navController: NavController? = null, // Optional for "button" variant
    onBackClick: (() -> Unit)? = null,    // Function called on back button click
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (variant == AppBarVariant.BUTTON) {
            IconButton(
                onClick = {
                    onBackClick?.invoke()
                    navController?.popBackStack() // Optional: Go back
                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.weui_arrow_filled), // Replace with your back arrow asset
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and title
        }

        Text(
            text = title,
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp, // Matches text-2xl in Tailwind
            ),
            modifier = Modifier.weight(1f) // Grows to fill available space
        )
    }
}
