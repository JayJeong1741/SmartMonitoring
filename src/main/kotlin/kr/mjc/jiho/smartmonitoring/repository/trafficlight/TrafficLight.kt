package kr.mjc.jiho.smartmonitoring.repository.trafficlight

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "trafficLight")
class TrafficLight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) var idn: String? = null
    val lat: BigDecimal? = null // decimal(9,6), nullable
    val lng: BigDecimal? = null // decimal(9,6), nullable
    lateinit var cName: String // varchar(20), 두 번째 PK
    lateinit var sName: String
    var state: Int? = null // int, nullable
    var lastEmergency: LocalDateTime? = null // datetime, nullable

    override fun toString(): String {
        return "Post(idn=$idn, cname=$cName, sName='$sName', lat='$lat', lng=$lng, state=$state, lastEmergency=$lastEmergency)"
    }
}
