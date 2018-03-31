package rankhep.com.notthatway.util

import android.os.PowerManager
import android.app.NotificationManager
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import rankhep.com.notthatway.R
import rankhep.com.notthatway.protector.ProtectorMainActivity


/**
 * Created by choi on 2018. 4. 1..
 */

class FirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {

    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

//        sendPushNotification(remoteMessage!!.data["message"]!!)
        sendPushNotification("위험지역에 들어갔습니다")
    }

    private fun sendPushNotification(message: String) {
        println("received message : " + message)
        val intent = Intent(this, ProtectorMainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("위험지역에 입장하셨습니다")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri).setLights(173, 500, 2000)
                .setContentIntent(pendingIntent)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val pm = this.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG")
        wakelock.acquire(5000)

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    companion object {
        private val TAG = "FirebaseMsgService"
    }

}