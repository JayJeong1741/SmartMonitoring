package kr.mjc.jiho.smartmonitoring.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class MonitoringController {

    @GetMapping("/trafficLight/dashboard", "/trafficLight/trafficDetail")
    fun pass(){}
}