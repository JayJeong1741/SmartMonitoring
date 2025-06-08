package kr.mjc.jiho.smartmonitoring.controller

import kr.mjc.jiho.smartmonitoring.DataClass.FcmDeviceUpdateStateDto
import kr.mjc.jiho.smartmonitoring.DataClass.LocationDto
import kr.mjc.jiho.smartmonitoring.repository.fcmDevices.FcmDevicesRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class FcmDevicesController(val fcmDevicesRepository: FcmDevicesRepository) {

    @PostMapping("/api/location")
    @ResponseBody
    @CrossOrigin(origins = ["*"])
    fun location(@RequestBody location: LocationDto) {
        val fcmDevice = fcmDevicesRepository.findByDeviceNameIdToken(location.deviceId, location.deviceName, location.fcmToken)

        if(fcmDevice != null) {
            fcmDevicesRepository.updateFcmDeviceLoc(location.deviceName, location.deviceId, location.lat, location.lng)
        }
    }


    @PostMapping("/api/updateState")
    @ResponseBody
    @CrossOrigin(origins = ["*"])
    fun updateState(@RequestBody fcmDeviceUpdateStateDto: FcmDeviceUpdateStateDto) {
        val fcmDevice = fcmDevicesRepository.findByDeviceNameIdToken(fcmDeviceUpdateStateDto.deviceId, fcmDeviceUpdateStateDto.deviceName, fcmDeviceUpdateStateDto.fcmToken)
        if(fcmDevice != null) {
            try{
                fcmDevicesRepository.updateState(fcmDeviceUpdateStateDto.deviceId, fcmDeviceUpdateStateDto.deviceName, fcmDeviceUpdateStateDto.state, fcmDeviceUpdateStateDto.fcmToken)
            }catch(e: Exception){
                println("업데이트 중 오류 발생 : $e")
            }

        }else{
            try {
                //deadlock발생 재구성 필요
                fcmDevicesRepository.insertNewFcmDevice(fcmDeviceUpdateStateDto.deviceId, fcmDeviceUpdateStateDto.deviceName, fcmDeviceUpdateStateDto.fcmToken, fcmDeviceUpdateStateDto.state)
            }catch (e: Exception){
                println("새로운 기기 등록 중 오류 발생 :  $e")
            }

        }
    }

}