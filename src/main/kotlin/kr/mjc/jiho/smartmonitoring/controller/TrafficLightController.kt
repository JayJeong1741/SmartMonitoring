package kr.mjc.jiho.smartmonitoring.controller

import jakarta.servlet.http.HttpSession
import kr.mjc.jiho.smartmonitoring.repository.trafficlight.TrafficLight
import kr.mjc.jiho.smartmonitoring.repository.trafficlight.TrafficLightRepository
import kr.mjc.jiho.smartmonitoring.repository.user.User
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.SessionAttribute


@Controller
class TrafficLightController(val trafficLightRepository: TrafficLightRepository) {

    companion object {
        private const val PAGE_SIZE = 20
    }

    @GetMapping("/trafficLight/dashboard")
    fun dashboard(@SessionAttribute user: User, model: Model) {
        val cid: Long? = user.constituencyId.id

        if (cid != null) {
            val normalState: Int = trafficLightRepository.countTrafficLightNormal(cid)
            val emergencyState: Int = trafficLightRepository.countTrafficLightEmergency(cid)
            val inspectionState: Int = trafficLightRepository.countTrafficLightInspection(cid)
            model.addAttribute("normalState", normalState)
            model.addAttribute("emergencyState", emergencyState)
            model.addAttribute("inspectionState", inspectionState)
            model.addAttribute("emergencyLocList", trafficLightRepository.findEmergencyLOC(cid))
            model.addAttribute("emergencyCount", trafficLightRepository.emergencyCountByCid(cid))
            println("data" + trafficLightRepository.emergencyCountByCid(cid))
        }
    }

    @GetMapping("/trafficLight/trafficList")
    fun list(@RequestParam page: Int=0,session: HttpSession ,model: Model){
        session.setAttribute("page", page)

        val user = session.getAttribute("user") as User
        val constituencyId = user.constituencyId.id //.toString은 항상 문자열 반환(빈 경우 null을 반환), User클래스의 cName은 Constitnecy를 참조.

        if(constituencyId != null){
            val trafficLights : Slice<TrafficLight> = trafficLightRepository.findByCid(constituencyId,PageRequest.of(page, PAGE_SIZE))
            model.addAttribute("trafficLights", trafficLights)
        }

    }

    @GetMapping("/trafficLight/trafficDetail")
    fun trafficDetail(id: Long,cid:Long, model: Model) {
        println("id:" + id)

        val trafficLight : TrafficLight? =trafficLightRepository.findByIdCid(id, cid)
        println("나오나? : " + trafficLight)
        model.addAttribute("trafficLight", trafficLight)
    }

    @GetMapping("/api/emergency_traffic_lights")
    @ResponseBody
    fun getTrafficLights(@SessionAttribute user:User): List<TrafficLight?>? {
        val cid = user.constituencyId.id

        return cid?.let { trafficLightRepository.findEmergencyLOC(it) }
    }

}