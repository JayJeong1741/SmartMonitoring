package kr.mjc.jiho.smartmonitoring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
@ServletComponentScan
class SmartMonitoringApplication : WebMvcConfigurer {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}



fun main(args: Array<String>) {
    runApplication<SmartMonitoringApplication>(*args)
}
