package kr.mjc.jiho.smartmonitoring.repository.trafficlight

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.LocalDateTime

data class TrafficLightDto(
    val sName: String,
    val emergencyCount: Int
)

data class LastEmergencyDto(
    val id : Long,
    val cid: Long,
    val sName: String,
    val lastEmergency:Timestamp
)

data class NearestLoc(
    val id: Long,
    val cid: Long,
    val lat: BigDecimal,
    val lng: BigDecimal,
    val sName:String,
    val distance: Double
)


interface TrafficLightRepository : JpaRepository<TrafficLight, String> {

    @Query("""
    SELECT t 
    FROM TrafficLight t 
    WHERE t.id.cid = :cid 
    ORDER BY
CASE 
    WHEN t.state = 1 THEN 0  
    WHEN t.state = 2 THEN 1  
    ELSE 2                 
  END,
  t.id.id ASC
    """)
    fun findByCid(@Param("cid") cid: Long, pageable: Pageable): Slice<TrafficLight>

    @Query("SELECT t FROM TrafficLight t WHERE t.id.cid = :cid AND t.state = 1")
    fun findEmergencyLOC(cid:Long): List<TrafficLight>?

    @Query("SELECT t FROM TrafficLight t WHERE t.id.id = :id AND t.id.cid = :cid")
    fun findByIdCid(id : Long, cid: Long) : TrafficLight?

    @Query("SELECT COUNT(*) From TrafficLight WHERE id.cid = :cid AND state = 0")
    fun countTrafficLightNormal(cid:Long) : Int

    @Query("SELECT COUNT(*) From TrafficLight WHERE id.cid = :cid AND state = 1")
    fun countTrafficLightEmergency(cid:Long) : Int

    @Query("SELECT COUNT(*) From TrafficLight WHERE id.cid = :cid AND state = 2")
    fun countTrafficLightInspection(cid:Long) : Int

    @Query(
        value = "SELECT s_name, emergency_count FROM traffic_light WHERE cid = :cid AND DATE(last_emergency) = CURRENT_DATE ORDER BY emergency_count DESC LIMIT 3",
        nativeQuery = true
    )
    fun emergencyCountByCid(cid: Long): List<TrafficLightDto>

    @Query(
        value = "SELECT id, cid, s_name, last_emergency FROM traffic_light WHERE cid = :cid ORDER BY last_emergency DESC LIMIT 2 ",
        nativeQuery = true
    )
    fun lastEmergency(cid: Long): List<LastEmergencyDto>


    @Modifying
    @Query("UPDATE TrafficLight t SET t.emergencyCount = 0 ")
    fun resetCount()

    @Modifying
    @Transactional
    @Query("UPDATE TrafficLight t SET t.state = 0 WHERE t.id.id = :id AND t.id.cid = :cid")
    fun setSafe(id:Long, cid:Long)

    @Modifying
    @Transactional
    @Query("UPDATE TrafficLight t SET t.state = 1 WHERE t.id.id = :id AND t.id.cid = :cid")
    fun setUnsafe(id:Long, cid:Long)

    @Modifying
    @Transactional
    @Query("UPDATE TrafficLight t SET t.state = 2 WHERE t.id.id = :id AND t.id.cid = :cid")
    fun setInspection(id:Long, cid:Long)


    @Query("""
        SELECT id, cid, lat, lng, s_name,
               (6371 * ACOS(SIN(RADIANS(:targetLat)) * SIN(RADIANS(lat)) +
                            COS(RADIANS(:targetLat)) * COS(RADIANS(lat)) * COS(RADIANS(lng) - RADIANS(:targetLon)))) AS distance
        FROM traffic_light
        WHERE id != :id
        ORDER BY distance
        LIMIT 3""", nativeQuery = true)
    fun findClosestTrafficLights(targetLat: BigDecimal, targetLon: BigDecimal, id:Long): List<NearestLoc>


}