package com.molohala.grow.application

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.molohala.grow.R
import com.molohala.grow.common.constant.TAG
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.data.notification.PostFcmTokenReq
import com.molohala.grow.ui.root.AppActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: 토큰이다 $token")
        try {
            val req = PostFcmTokenReq(fcmToken = token)
            CoroutineScope(Dispatchers.IO).launch {
                RetrofitClient.notificationApi.postFcmToken(req)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d(TAG, "onMessageReceived Data: ${message.data} ")
        Log.d(TAG, "onMessageReceived Noti: ${message.notification?.title}  and ${message.notification?.body} ")

        // 알림 현재 상태
//        var status: Boolean
//        runBlocking {
//            alarmRepository.getAlarmState().let {
//                status = it
//            }
//        }

        val title = message.notification?.title
        val body = message.notification?.body
        val data = message.data
        val type = data["type"]
        Log.d(TAG, "title: $title, body: $body, data: $data, type: $type ")

        // 알림 권한 창
        createNotificationChannel()

        // 알림을 클릭했을 때 열릴 액티비티 지정
        val intent = Intent(this, AppActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // 알림 소리
        val defaultSoundUri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, "Grow_default_channels")
            .setSmallIcon(R.mipmap.ic_launcher) // 알림 아이콘
            .setContentTitle("그로우") // 알림 제목
            .setContentText("$body") // 알림 내용
            .setAutoCancel(true) // 알림을 클릭하면 자동으로 닫힘
            .setSound(defaultSoundUri) // 알림 소리
            .setContentIntent(pendingIntent) // 알림 클릭 시 실행될 Intent
            .setColor(Color.WHITE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())
    }


    // 알림 권한 창
    private fun createNotificationChannel() {
        val name = "Grow Default Channel"
        val descriptionText = "Grow Default Channel Description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("Grow_default_channels", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}