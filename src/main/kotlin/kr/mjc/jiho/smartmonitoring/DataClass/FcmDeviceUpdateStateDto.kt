package kr.mjc.jiho.smartmonitoring.DataClass

data class FcmDeviceUpdateStateDto(
    val deviceId: String,
    val deviceName: String,
    val fcmToken: String,
    val state: Int
)
