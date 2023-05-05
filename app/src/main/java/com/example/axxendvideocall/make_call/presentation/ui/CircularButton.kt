package com.example.axxendvideocall.make_call.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.axxendvideocall.MainActivity
import com.example.axxendvideocall.R
import com.example.axxendvideocall.make_call.MessagingService
import com.example.axxendvideocall.make_call.presentation.MakeCallResource
import com.example.axxendvideocall.make_call.presentation.ui.theme.backgroundGradient
import com.example.axxendvideocall.make_call.presentation.ui.theme.backgroundGradient0
import com.example.axxendvideocall.make_call.presentation.ui.theme.inactiveBackgroundGradient
import com.example.axxendvideocall.make_call.presentation.ui.theme.inactiveBackgroundGradient0
import kotlinx.coroutines.runBlocking


@Composable
fun CircularButton(state: MakeCallResource, context: Context) {

    Box(
        Modifier
            .fillMaxWidth(0.85f)
            .aspectRatio(1f)
            .background(
                brush = if(state.isCallAvailable) backgroundGradient0 else inactiveBackgroundGradient0,
                shape = CircleShape
            ),
        Alignment.Center
    ) {

        Box(
            Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
                .background(
                    brush = if (state.isCallAvailable) backgroundGradient else inactiveBackgroundGradient,
                    shape = CircleShape
                ), Alignment.Center
        ) {

            Box(
                Modifier

                    .fillMaxWidth(0.65f)
                    .aspectRatio(1f)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape
                    )
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.secondary,
                        shape = CircleShape
                    )
                    .clickable(
                        indication = rememberRipple(
                            color = MaterialTheme.colorScheme.primary,
                            bounded = false
                        ),
                        interactionSource = MutableInteractionSource(),
                        onClick = {

                            runBlocking {
                                MessagingService().sendNotification(
                                    context = context,
                                    messageBody = "From a random user",
                                    channel_id = "channel02",
                                    title = "Outgoing Call"
                                )
                            }

                            val intent = Intent(
                                context,
                                MainActivity::class.java
                            )
                            context.startActivity(intent)
                        }

                    ), Alignment.Center
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "Camera",
                    tint = MaterialTheme.colorScheme.secondary
                )

            }

        }

    }
}