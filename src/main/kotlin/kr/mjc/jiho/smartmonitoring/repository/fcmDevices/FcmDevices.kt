package kr.mjc.jiho.smartmonitoring.repository.fcmDevices

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime


@Entity
@Table(
    name = "fcm_devices",
    uniqueConstraints = [
        UniqueConstraint(
            columnNames = ["deviceId", "deviceName", "fcmToken"]
        )
    ]
)
class FcmDevices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0

    lateinit var deviceId: String
    lateinit var deviceName: String
    lateinit var fcmToken: String
    lateinit var lat: BigDecimal
    lateinit var lng: BigDecimal
    var lastUpdatedAt: LocalDateTime = LocalDateTime.now()
    var state: Int = 0

    override fun toString(): String {
        return "FcmDevices(id=${id}, deviceId=${deviceId}, deviceName=${deviceName}, fcmToken=${fcmToken}, lat=${lat}, lng=${lng}, lastUpdatedAt=${lastUpdatedAt}, state=${state})"
    }
}