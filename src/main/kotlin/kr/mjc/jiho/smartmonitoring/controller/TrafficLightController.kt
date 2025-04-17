package kr.mjc.jiho.smartmonitoring.controller

import jakarta.servlet.http.HttpSession
import kr.mjc.jiho.smartmonitoring.repository.Constituency
import kr.mjc.jiho.smartmonitoring.repository.trafficlight.TrafficLight
import kr.mjc.jiho.smartmonitoring.repository.trafficlight.TrafficLightRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class TrafficLightController(val trafficLightRepository: TrafficLightRepository) {

    companion object {
        private const val PAGE_SIZE = 20
    }

    @GetMapping("/trafficLight/trafficList")
    fun list(@RequestParam page: Int=0,session: HttpSession ,model: Model){
        session.setAttribute("page", page)
        val constituency = session.getAttribute("constituency") as Constituency
            val cName = constituency.cName
        print("이게멀까" + cName);
        if(!cName.isNullOrEmpty()){
            val trafficLights : Slice<TrafficLight> = trafficLightRepository.findByIdnStartingWith(cName,PageRequest.of(page, PAGE_SIZE))
            model.addAttribute("trafficLights", trafficLights)
        }
    }
}