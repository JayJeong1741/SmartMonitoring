package kr.mjc.jiho.smartmonitoring.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController {
    @GetMapping("/test/api")
    fun test(): String {
        return "테스트입니다잉"
    }
}