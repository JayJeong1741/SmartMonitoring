package kr.mjc.jiho.smartmonitoring.repository.trafficlight

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TrafficLightRepository : JpaRepository<TrafficLight, String> {




    @Query("SELECT t FROM TrafficLight t WHERE t.id.cid = :cid")
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

}