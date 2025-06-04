package kr.mjc.jiho.smartmonitoring.DataClass

data class NearestWorkerDto(
    val deviceId: String,
    val deviceName: String,
    val fcmToken: String,
    val distance: Double
)
