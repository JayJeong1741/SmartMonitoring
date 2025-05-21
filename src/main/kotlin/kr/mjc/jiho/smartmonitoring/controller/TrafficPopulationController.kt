package kr.mjc.jiho.smartmonitoring.controller

import kr.mjc.jiho.smartmonitoring.repository.trafficPopulation.TrafficPopulation
import kr.mjc.jiho.smartmonitoring.repository.trafficPopulation.TrafficPopulationRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
class TrafficPopulationController(val trafficPopulationRepository: TrafficPopulationRepository) {

    @PostMapping("/api/traffic")
    @ResponseBody
    @CrossOrigin(origins = ["*"])
    fun traffic(@RequestBody trafficPopulation: TrafficPopulation) {
        trafficPopulationRepository.insertPopulation(trafficPopulation.id.id, trafficPopulation.id.cid,
            trafficPopulation.id.date, trafficPopulation.population)
    }
}