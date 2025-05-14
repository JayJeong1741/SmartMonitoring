package kr.mjc.jiho.smartmonitoring.repository.trafficlight

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime


@Entity
@Table(name = "trafficPopulation")
class TrafficPopulation {
    @EmbeddedId
    val id : TrafficPopulationId = TrafficPopulationId()
    val population: Int = 0

    override fun toString(): String {
        return "TrafficLight(idn=${id.id}, cid=${id.cid}, population = ${population})"
    }
}