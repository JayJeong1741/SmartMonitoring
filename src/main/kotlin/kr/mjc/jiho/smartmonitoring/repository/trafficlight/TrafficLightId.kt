package kr.mjc.jiho.smartmonitoring.repository.trafficlight
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class TrafficLightId(
    val id: Long = 0,
    val cid: Long = 0
) : Serializable