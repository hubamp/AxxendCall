package com.example.axxendvideocall.make_call.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.axxendvideocall.R
import com.example.axxendvideocall.make_call.presentation.MakeCallResource

@Composable
fun BottomCallButtons(modifier:Modifier = Modifier, state:MakeCallResource){

    Box(
        modifier
            .fillMaxWidth()

    ) {

        val callButtonShape = RoundedCornerShape(
            topStartPercent = 100,
            topEndPercent = 100,

            )

        Box(
            Modifier
                .fillMaxWidth(0.53f)
                .aspectRatio(2f)
                .align(Alignment.BottomStart)
                .border(
                    2.dp,
                    color = MaterialTheme.colorScheme.error,
                    shape = callButtonShape
                )
                .background(
                    shape = callButtonShape,
                    color = MaterialTheme.colorScheme.errorContainer
                ),
        ) {

            Icon(
                painter = painterResource(id = R.drawable.end),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)

            )

            state.message?.let {
                Text(
                    text = "Decline",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(
                        Alignment.BottomCenter
                    )
                )
            }

        }


        Box(
            Modifier
                .fillMaxWidth(0.53f)
                .aspectRatio(2f)
                .align(Alignment.BottomEnd)
                .background(
                    shape = callButtonShape,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                .border(
                    2.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = callButtonShape
                )
        ) {

            Icon(
                painter = painterResource(id = R.drawable.call),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.align(Alignment.Center)
            )

            Text(
                text = "Accept",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.align(
                    Alignment.BottomCenter
                )
            )


        }


    }
}