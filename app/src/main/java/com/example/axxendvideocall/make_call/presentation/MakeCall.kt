package com.example.axxendvideocall.make_call.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.axxendvideocall.MainActivity
import com.example.axxendvideocall.R
import com.example.axxendvideocall.make_call.MessagingService
import com.example.axxendvideocall.make_call.presentation.ui.BottomCallButtons
import com.example.axxendvideocall.make_call.presentation.ui.CircularButton
import com.example.axxendvideocall.make_call.presentation.ui.theme.AxxendVideoCallTheme
import com.example.axxendvideocall.make_call.presentation.ui.theme.backgroundGradient
import com.example.axxendvideocall.make_call.presentation.ui.theme.backgroundGradient0
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

@AndroidEntryPoint
class MakeCall : ComponentActivity() {


    val msg = MessagingService()

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }


    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MakeCallViewModel = hiltViewModel()

            val state = viewModel.makeCallState.collectAsState()


            AxxendVideoCallTheme {


                val context = LocalContext.current


                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.White
                ) {


                    Box(modifier = Modifier.fillMaxSize()) {


                        Image(
                            painter = painterResource(id = R.drawable.shadowey),
                            contentDescription = "",
                            modifier = Modifier
                                .align(
                                    Alignment.TopEnd
                                )
                                .size(250.dp)
                                .padding(0.dp),
                            contentScale = ContentScale.FillBounds
                        )

                        Column(
                            Modifier
                                .wrapContentSize()
                                .align(Alignment.TopCenter)
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Logo",
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Text(text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("AXXEND ")
                                }
                                append("CALL")

                            }, color = MaterialTheme.colorScheme.primary)
                            Text(
                                text = "CALL THE LAST ACTIVE USER",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 8.sp
                            )
                        }


                        Column(
                            Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            CircularButton(state = state.value, context = context)

                            Text(
                                text = "Call The Last Active User",
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(5.dp)
                            )

                            state.value.message?.let { message ->
                                Text(
                                    text = message,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }


                        BottomCallButtons(
                            state = state.value, modifier = Modifier.align(Alignment.BottomCenter)
                        )

                    }
                }
            }
        }
    }
}