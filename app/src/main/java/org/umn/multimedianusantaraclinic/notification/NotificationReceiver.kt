package org.umn.multimedianusantaraclinic.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(EXTRA_ID, 2001)
        val title = intent.getStringExtra(EXTRA_TITLE) ?: "UMN Clinic Reminder"
        val message = intent.getStringExtra(EXTRA_MESSAGE) ?: "You have a clinic update."

        ClinicNotificationHelper.showInstantNotification(
            context = context,
            notificationId = id,
            title = title,
            message = message
        )
    }

    companion object {
        const val EXTRA_ID = "extra_notif_id"
        const val EXTRA_TITLE = "extra_notif_title"
        const val EXTRA_MESSAGE = "extra_notif_message"
    }
}