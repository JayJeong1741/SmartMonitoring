package kr.mjc.jiho.smartmonitoring.repository.trafficPopulation

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table


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