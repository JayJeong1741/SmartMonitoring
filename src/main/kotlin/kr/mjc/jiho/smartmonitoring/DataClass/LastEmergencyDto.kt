package kr.mjc.jiho.smartmonitoring.DataClass

import java.sql.Timestamp

data class LastEmergencyDto(
    val id : Long,
    val cid: Long,
    val sName: String,
    val lastEmergency: Timestamp
)