package com.example.footballapidemo

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
//将UTC时间转换为字符串格式的中国时间
fun convertUtcToChinaDate(utcTimeString: String?): String {
    val utcDateTime = LocalDateTime.parse(utcTimeString, DateTimeFormatter.ISO_DATE_TIME)
    val utcZoneId = ZoneId.of("UTC")
    val chinaZoneId = ZoneId.of("Asia/Shanghai")
    val utcZonedDateTime = ZonedDateTime.of(utcDateTime, utcZoneId)
    val chinaZonedDateTime = utcZonedDateTime.withZoneSameInstant(chinaZoneId)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return formatter.format(chinaZonedDateTime).toString()
}

@RequiresApi(Build.VERSION_CODES.O)
//将字符串格式的UTC时间转换为中国日期
fun convertUtcToChinaTime(utcTimeString: String?): String {
    val utcDateTime = LocalDateTime.parse(utcTimeString, DateTimeFormatter.ISO_DATE_TIME)
    val utcZoneId = ZoneId.of("UTC")
    val chinaZoneId = ZoneId.of("Asia/Shanghai")
    val utcZonedDateTime = ZonedDateTime.of(utcDateTime, utcZoneId)
    val chinaZonedDateTime = utcZonedDateTime.withZoneSameInstant(chinaZoneId)
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return formatter.format(chinaZonedDateTime)
}

@RequiresApi(Build.VERSION_CODES.O)
//获取当前日期
fun getCurrentLocalDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return currentDate.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDateWithOffset(date: String, days: Long, isForward: Boolean): String {
    // 将输入的日期字符串解析为 LocalDate 对象
    val currentDate = LocalDate.parse(date)

    // 根据isForward来决定添加或减去指定的天数
    val resultDate = if (isForward) {
        currentDate.plusDays(days)
    } else {
        currentDate.minusDays(days)
    }

    // 格式化日期并返回
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return resultDate.format(formatter)
}