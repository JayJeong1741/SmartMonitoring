package kr.mjc.jiho.smartmonitoring.DataClass

import java.math.BigDecimal

data class TrafficLightLoc(
    val id: Long,
    val cid: Long,
    val lat: BigDecimal,
    val lng: BigDecimal,
    val sName:String,
    val state: Int,
    val distance: Double
)