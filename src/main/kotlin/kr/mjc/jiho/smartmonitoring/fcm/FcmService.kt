package kr.mjc.jiho.smartmonitoring.fcm

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Service

@Service
class FCMService {
    fun sendNotification(targetToken: String?, title: String?, body: String?): String {
        return try {
            val notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build()

            val message = Message.builder()
                .setToken(targetToken)
                .setNotification(notification)
                .build()

            FirebaseMessaging.getInstance().send(message)
        } catch (e: Exception) {
            e.printStackTrace()
            "Failed to send message"
        }
    }
}
