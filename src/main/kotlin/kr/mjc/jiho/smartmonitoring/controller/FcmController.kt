package kr.mjc.jiho.smartmonitoring.controller

import kr.mjc.jiho.smartmonitoring.fcm.FCMService
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
        try {
            return fcmService!!.sendNotification(token, title, body)
        }catch (e:Exception){
            println(e)
            return e.message!!
        }

    }
}