package kr.mjc.jiho.smartmonitoring.repository.trafficlight

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
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


interface TrafficLightRepository : JpaRepository<TrafficLight, String> {

    @Query(
        """
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
    """
    )
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
}