package kr.mjc.jiho.smartmonitoring.repository.trafficPopulation

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.time.LocalDateTime


@Embeddable
data class TrafficPopulationId(
    val id : Long = 0,
    val cid : Long = 0,
    val date : LocalDateTime = LocalDateTime.now()
): Serializable {}