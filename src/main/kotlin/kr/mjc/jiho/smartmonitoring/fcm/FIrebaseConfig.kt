package kr.mjc.jiho.smartmonitoring.fcm

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream
import javax.annotation.PostConstruct


@Configuration
class FirebaseConfig {
    @PostConstruct
    fun initialize() {
        try {
            val serviceAccount = FileInputStream("src/main/resources/fcmtest-bc925-firebase-adminsdk-fbsvc-04bb36c9cf.json")

            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

