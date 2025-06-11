package kr.mjc.jiho.smartmonitoring.repository.fcmDevices

import kr.mjc.jiho.smartmonitoring.DataClass.NearestWorkerDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

interface FcmDevicesRepository : JpaRepository<FcmDevices, Long> {

    @Query("SELECT t FROM FcmDevices t WHERE t.deviceName = :deviceName")
    fun findByDeviceName(deviceName: String): FcmDevices?



    @Query("SELECT t FROM FcmDevices t WHERE t.deviceName = :deviceName and t.deviceId = :deviceId")
    fun findByDeviceNameIdToken(deviceId: String, deviceName: String, fcmToken: String): FcmDevices?


    @Modifying
    @Transactional
    @Query("UPDATE FcmDevices t SET t.lastUpdatedAt = CURRENT_TIMESTAMP , t.lat = :lat, t.lng = :lng WHERE t.deviceName = :deviceName AND t.deviceId = :deviceId")
    fun updateFcmDeviceLoc(deviceName: String, deviceId: String, lat:BigDecimal, lng:BigDecimal)

    @Modifying
    @Transactional
    @Query("INSERT INTO fcm_devices (device_id, device_name, fcm_token, last_updated_at, state) VALUES (:deviceId, :deviceName, :fcmToken, CURRENT_TIMESTAMP, :state)",
        nativeQuery = true)
    fun insertNewFcmDevice(deviceId: String, deviceName: String, fcmToken: String, state:Int)


    @Query("""
        SELECT device_id, device_name, fcm_token,
               ROUND(
         6371 * ACOS(
           SIN(RADIANS(:targetLat)) * SIN(RADIANS(lat)) +
           COS(RADIANS(:targetLat)) * COS(RADIANS(lat)) * COS(RADIANS(lng) - RADIANS(:targetLon))
         ), 2
       ) AS distance
        FROM fcm_devices
        where state = 1
        HAVING distance <= 3.0
        ORDER BY distance
        LIMIT 3""", nativeQuery = true)
    fun findClosestWorker(targetLat: BigDecimal, targetLon: BigDecimal): List<NearestWorkerDto>

    @Modifying
    @Transactional
    @Query("UPDATE FcmDevices t SET t.state = :state WHERE t.deviceId = :deviceId AND t.deviceName = :deviceName AND t.fcmToken = :fcmToken")
    fun updateState(deviceId: String, deviceName: String ,state: Int, fcmToken: String): Int
}