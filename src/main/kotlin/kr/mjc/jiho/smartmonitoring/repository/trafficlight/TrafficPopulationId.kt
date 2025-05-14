package kr.mjc.jiho.smartmonitoring.repository.trafficlight

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date


@Embeddable
data class TrafficPopulationId(
    val id : Long = 0,
    val cid : Long = 0,
    val date : LocalDateTime = LocalDateTime.now()
): Serializable {}