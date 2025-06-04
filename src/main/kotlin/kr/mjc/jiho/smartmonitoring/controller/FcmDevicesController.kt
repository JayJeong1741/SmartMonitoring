package kr.mjc.jiho.smartmonitoring.controller

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

        val fcmDevice = fcmDevicesRepository.findByDeviceNameId(location.deviceId, location.deviceName)

        if(fcmDevice != null) {
            if(fcmDevice.fcmToken != location.fcmToken) {
                fcmDevicesRepository.updateFcmToken(fcmDevice.deviceId, fcmDevice.deviceName, fcmDevice.fcmToken)
            }
            fcmDevicesRepository.updateFcmDeviceLoc(location.deviceName, location.deviceId, location.lat, location.lng)
        }
        else{
            fcmDevicesRepository.insertNewFcmDevice(location.deviceId, location.deviceName, location.fcmToken,location.lat, location.lng)
        }
    }

}