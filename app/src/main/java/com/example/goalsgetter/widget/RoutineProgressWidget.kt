package com.example.goalsgetter.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.ButtonDefaults
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.goalsgetter.ui.MainActivity
import com.example.goalsgetter.R

class RoutineProgressWidget : GlanceAppWidget() {

    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(Color(0xFF26A69A))
                    .padding(12.dp),
            ) {
                Image(
                    provider = ImageProvider(R.drawable.quote_svgrepo_com), // Replace with your vector drawable
                    contentDescription = "Routine Icon",
                    modifier = GlanceModifier.size(48.dp)
                )

                Spacer(GlanceModifier.height(12.dp))

                Text(
                    text = "Have you done your routine today?",
                    style = TextStyle(
                        color = ColorProvider(Color.White),
                        fontSize = 24.sp
                    ),
                )

                Spacer(GlanceModifier.height(28.dp))

                Button(
                    text = "Start here",
                    onClick = {
                        actionStartActivity(
                            Intent(context, MainActivity::class.java)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = ColorProvider(Color.White),
                        contentColor = ColorProvider(Color(0xFF26A69A)),
                    )
                )
            }
        }
    }

}
