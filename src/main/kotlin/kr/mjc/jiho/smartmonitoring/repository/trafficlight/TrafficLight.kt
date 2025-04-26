package kr.mjc.jiho.smartmonitoring.repository.trafficlight

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "trafficLight")
class TrafficLight {
    @EmbeddedId
    val id: TrafficLightId = TrafficLightId() // 복합 키 객체 사용

    val lat: BigDecimal? = null // decimal(9,6), nullable
    val lng: BigDecimal? = null // decimal(9,6), nullable
    lateinit var sName: String
    var state: Int? = null // int, nullable
    var lastEmergency: LocalDateTime? = null // datetime, nullable
    var emergencyCount: Int = 0;

    override fun toString(): String {
        return "TrafficLight(idn=${id.id}, cid=${id.cid}, sName='$sName', lat='$lat', lng=$lng, state=$state, lastEmergency=$lastEmergency," +
                "emergency_count=${emergencyCount})"
    }
}
