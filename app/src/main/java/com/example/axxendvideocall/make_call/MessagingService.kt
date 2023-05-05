package com.example.axxendvideocall.make_call

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat
import com.example.axxendvideocall.MainActivity
import com.example.axxendvideocall.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }


    override fun onMessageReceived(message: RemoteMessage) {
//        super.onMessageReceived(message)

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("TAG", "From: ${message.from}")

        // Check if message contains a data payload.
        if (message.data.isNotEmpty()) {
            Log.d("TAG", "Message data payload: ${message.data}")

            Toast.makeText(this, "Hadoo", Toast.LENGTH_LONG).show()


            sendNotification(
                this,
                message.data.values.toString(),
                channel_id = "channel01",
                title = message.data["title"]
            )

            /* // Check if data needs to be processed by long running job
             if (needsToBeScheduled()) {
                 // For long-running tasks (10 seconds or more) use WorkManager.
                 scheduleJob()
             } else {
                 // Handle message within 10 seconds
                 handleNow()
             }*/
        }

        // Check if message contains a notification payload.
        message.notification?.let {
            Log.d("TAG", "Message Notification Body: ${it.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


    fun sendNotification(
        context: Context = applicationContext,
        messageBody: String?,
        channel_id: String?,
        title: String?
    ) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val requestCode = 0
        val pendingIntent = PendingIntent.getActivity(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_MUTABLE,
        )

        val acceptAction = NotificationCompat.Action(
            IconCompat.createWithResource(context, R.drawable.call),
            "Accept",
            pendingIntent
        )

        val declineAction = NotificationCompat.Action(
            IconCompat.createWithResource(context, R.drawable.end),
            "Decline",
            pendingIntent
        )

        val channelId = channel_id

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val notificationBuilder = NotificationCompat.Builder(context, channelId.toString())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setOngoing(true)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.CallStyle())
            .setVibrate(LongArray(7) {
                500
            })
            .addAction(declineAction)
            .addAction(acceptAction)
            .setSound(defaultSoundUri)
            .setFullScreenIntent(pendingIntent, true)
            .setContentIntent(pendingIntent)


        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "channelName",
                NotificationManager.IMPORTANCE_HIGH,
            )
            notificationManager.createNotificationChannel(channel)
        }


        val notificationId = 3
        notificationManager.notify(notificationId, notificationBuilder.build())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
            context.startService(intent)
        }
    }


}