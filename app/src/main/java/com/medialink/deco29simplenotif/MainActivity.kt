package com.medialink.deco29simplenotif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat
import coil.Coil
import coil.api.load
import coil.transform.CircleCropTransformation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        val NOTIFICATION_ID = 1
        var CHANNEL_ID = "channel_01"
        var CHANNEL_NAME: CharSequence = "Dicoding Channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.button) {
            sendNotification()
        }
    }

    private fun sendNotification() {


//        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.benk100)

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://picsum.photos/"))
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val urlImage = "https://picsum.photos/100"
        Coil.load(this, urlImage) {
            size(100, 100)
            //transformations(CircleCropTransformation())
            target { result ->
                val largeIcon = (result as BitmapDrawable).bitmap

                val builder = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setLargeIcon(largeIcon)
                    .setContentTitle(getString(R.string.content_title))
                    .setContentText(getString(R.string.content_text))
                    .setSubText(getString(R.string.subtext))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)


                // untuk oreo ke atas, tambahkan notification channel
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT)
                    notifManager.createNotificationChannel(channel)

                    builder.setChannelId(CHANNEL_ID)
                }

                val notification = builder.build()
                notifManager.notify(NOTIFICATION_ID, notification)
            }
        }
    }
}
