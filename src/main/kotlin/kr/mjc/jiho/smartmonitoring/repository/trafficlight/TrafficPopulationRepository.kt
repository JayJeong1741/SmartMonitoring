package kr.mjc.jiho.smartmonitoring.repository.trafficlight

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

interface TrafficPopulationRepository : JpaRepository<TrafficPopulation, Long> {


    @Modifying
    @Transactional
    @Query("INSERT INTO traffic_population (id, cid, date, population) VALUES (:id, :cid, :date, :population)",
    nativeQuery = true
    )
    fun insertPopulation(id : Long, cid : Long, date : LocalDateTime, population : Int): Int
}