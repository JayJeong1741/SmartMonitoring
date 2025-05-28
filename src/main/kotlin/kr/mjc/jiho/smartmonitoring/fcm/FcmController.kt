package kr.mjc.jiho.smartmonitoring.fcm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/fcm")
class FCMController {
    @Autowired
    private val fcmService: FCMService? = null

    @PostMapping("/send")
    fun send(
        @RequestParam token: String?,
        @RequestParam title: String?,
        @RequestParam body: String?
    ): String {
        return fcmService!!.sendNotification(token, title, body)
    }
}