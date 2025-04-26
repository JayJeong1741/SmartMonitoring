package kr.mjc.jiho.smartmonitoring

import kr.mjc.jiho.smartmonitoring.repository.trafficlight.TrafficLightRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ResetService(private val trafficLightRepository: TrafficLightRepository) {
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    fun resetCount(){
        trafficLightRepository.resetCount()
    }
}