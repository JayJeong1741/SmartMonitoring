package kr.mjc.jiho.smartmonitoring.repository.trafficlight

import kr.mjc.jiho.smartmonitoring.DataClass.LastEmergencyDto
import kr.mjc.jiho.smartmonitoring.DataClass.TrafficLightDto
import kr.mjc.jiho.smartmonitoring.DataClass.TrafficLightLoc
import kr.mjc.jiho.smartmonitoring.controller.TrafficLightController
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime


interface TrafficLightRepository : JpaRepository<TrafficLight, Long> {

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

    @Query("SELECT t FROM TrafficLight t WHERE t.id.cid = :cid")
    fun findTrafficLightLOC(cid:Long): List<TrafficLight>?

    @Query("SELECT t FROM TrafficLight  t WHERE t.id.cid = :cid AND t.state = 1")
    fun findEmergencyTrafficLightLoc(cid:Long): List<TrafficLight>?

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
    @Query(
        """
    UPDATE TrafficLight t 
    SET 
        t.state = :state, 
        t.emergencyCount = CASE 
        WHEN :state = 1 THEN t.emergencyCount + 1
        ELSE t.emergencyCount 
        END,
        t.lastEmergency = CASE 
        WHEN :state = 1 THEN :now ELSE t.lastEmergency END
    WHERE 
    t.id.id = :id AND t.id.cid = :cid
    """
    )
    fun updateState(id: Long, cid: Long, state: Int, now: LocalDateTime): Int

    @Query("""
        SELECT id, cid, lat, lng, s_name, state,
               (6371 * ACOS(SIN(RADIANS(:targetLat)) * SIN(RADIANS(lat)) +
                            COS(RADIANS(:targetLat)) * COS(RADIANS(lat)) * COS(RADIANS(lng) - RADIANS(:targetLon)))) AS distance
        FROM traffic_light
        WHERE NOT id = :id AND cid = :cid
        ORDER BY distance
        LIMIT 3""", nativeQuery = true)
    fun findClosestTrafficLights(targetLat: BigDecimal, targetLon: BigDecimal, id:Long, cid:Long): List<TrafficLightLoc>

}