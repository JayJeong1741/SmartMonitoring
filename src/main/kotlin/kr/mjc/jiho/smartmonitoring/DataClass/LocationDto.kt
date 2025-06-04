package kr.mjc.jiho.smartmonitoring.DataClass

import java.math.BigDecimal

data class LocationDto(
    val deviceId:String,
    val deviceName:String,
    val fcmToken:String,
    val lat: BigDecimal,
    val lng: BigDecimal
)
