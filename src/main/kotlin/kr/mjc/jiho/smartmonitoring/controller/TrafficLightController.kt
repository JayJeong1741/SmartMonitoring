package kr.mjc.jiho.smartmonitoring.controller

import com.fasterxml.jackson.annotation.JsonTypeInfo
import jakarta.servlet.http.HttpSession
import kr.mjc.jiho.smartmonitoring.repository.trafficlight.TrafficLight
import kr.mjc.jiho.smartmonitoring.repository.trafficlight.TrafficLightRepository
import kr.mjc.jiho.smartmonitoring.repository.user.User
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

data class TrafficInfo(
    val id: Long,
    val cid: Long,
    val state: Int
)


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
            model.addAttribute("lastEmergency", trafficLightRepository.lastEmergency(cid))
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

        val trafficLight : TrafficLight? =trafficLightRepository.findByIdCid(id, cid)

        if (trafficLight != null) {
            if(trafficLight.lat != null && trafficLight.lng != null) {
                model.addAttribute("nearestLoc",
                    trafficLightRepository.findClosestTrafficLights(trafficLight.lat, trafficLight.lng, id)
                )
            }
            model.addAttribute("trafficLight", trafficLight)
        }
    }



    @GetMapping("/api/emergency_traffic_lights")//대시보드 지도 위치 업데이트 api
    @ResponseBody
    @CrossOrigin(origins = ["*"])
    fun getTrafficLights(@SessionAttribute user:User): List<TrafficLight?>? {
        val cid = user.constituencyId.id

        return cid?.let { trafficLightRepository.findEmergencyLOC(it) }
    }

    @PostMapping("/api/state_update")
    @ResponseBody
    @CrossOrigin(origins = ["*"])
    fun updateTrafficLightState(@RequestBody trafficInfo: TrafficInfo, model: Model): String {
        println("trafficState:" + trafficInfo.state)
        try {
            //trafficLightRepository.updateState(trafficInfo.id, trafficInfo.cid, trafficInfo.state)
            println("업데이트 실행 완료")
            val updatedRows = trafficLightRepository.updateState(trafficInfo.id, trafficInfo.cid, trafficInfo.state)
            println("업데이트된 행 수: $updatedRows")
            return "{\"message\": \"신호 상태가 성공적으로 업데이트되었습니다.\"}"

        } catch (e: Exception) {
            println("\"업데이트 중 예외 발생: ${e.message}\"")
            return ("업데이트 중 예외 발생: ${e.message}")
        }

    }
}