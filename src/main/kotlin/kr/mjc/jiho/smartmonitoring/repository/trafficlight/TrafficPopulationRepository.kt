package kr.mjc.jiho.smartmonitoring.repository.trafficlight

import kr.mjc.jiho.smartmonitoring.DataClass.PopulationSummary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

interface TrafficPopulationRepository : JpaRepository<TrafficPopulation, Long> {


    @Modifying
    @Transactional
    @Query("INSERT INTO traffic_population (id, cid, date, population) VALUES (:id, :cid, :date, :population)",
    nativeQuery = true
    )
    fun insertPopulation(id : Long, cid : Long, date : LocalDateTime, population : Int): Int



    @Query("""
    SELECT DATE(date) AS day, SUM(population) AS total_population
    FROM traffic_population
    WHERE date >= CURDATE() - INTERVAL 7 DAY
      AND cid = :cid AND id = :id
    GROUP BY DATE(date)
    ORDER BY day
""", nativeQuery = true)
    fun populationData(@Param("cid") cid: Long, @Param("id") id: Long): List<PopulationSummary>
}