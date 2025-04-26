package kr.mjc.jiho.smartmonitoring

import kr.mjc.jiho.smartmonitoring.Interceptor.AuthInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
class SmartMonitoringApplication : WebMvcConfigurer {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Autowired lateinit var authInterceptor: AuthInterceptor


    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/trafficLight/dashboard", "/trafficLight/trafficList", "/trafficLight/trafficDetail")
    }
}



fun main(args: Array<String>) {
    runApplication<SmartMonitoringApplication>(*args)
}
