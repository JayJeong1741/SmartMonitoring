package kr.mjc.jiho.smartmonitoring.repository.trafficlight

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TrafficLightRepository : JpaRepository<TrafficLight, String> {

    fun findByIdnStartingWith(cName: String, pageable: Pageable): Slice<TrafficLight>


}