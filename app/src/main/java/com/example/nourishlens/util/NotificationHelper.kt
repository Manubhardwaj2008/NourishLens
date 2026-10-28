package com.example.nourishlens.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.nourishlens.R

object NotificationHelper {

    private const val CHANNEL_ID = "nourishlens_expiry_alerts"
    private const val CHANNEL_NAME = "Product Expiration Alerts"

    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifies inspectors and users when scanned commodities approach or pass expiration dates."
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun triggerExpiryNotification(context: Context, productName: String, expiryDateStr: String, isExpired: Boolean) {
        createNotificationChannel(context)

        val title = if (isExpired) "⚠ EXPIRED PRODUCT ALERT!" else "⏰ NEAR EXPIRATION WARNING"
        val message = if (isExpired) {
            "$productName expired on $expiryDateStr. Sale of expired commodities is strictly prohibited under FSSAI Section 52."
        } else {
            "$productName is near expiration ($expiryDateStr). Ensure stock clearance or audit inspection."
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify((System.currentTimeMillis() % 10000).toInt(), builder.build())
        }
    }
}
