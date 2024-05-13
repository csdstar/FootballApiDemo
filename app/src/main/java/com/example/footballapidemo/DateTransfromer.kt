@file:RequiresApi(Build.VERSION_CODES.O)

package com.example.footballapidemo

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

//将UTC时间转换为字符串格式的中国时间
fun convertUtcToChinaLocalDate(utcTimeString: String?): LocalDate {
    // 解析UTC时间字符串为Instant对象
    val utcTime = Instant.parse(utcTimeString)
    // 获取中国时区
    val chinaZoneId = ZoneId.of("Asia/Shanghai")
    // 将UTC时间转换为中国时区时间
    val chinaTime = ZonedDateTime.ofInstant(utcTime, chinaZoneId)
    return chinaTime.toLocalDate()
}

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


//获取当前日期
fun getCurrentDateString(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return currentDate.format(formatter)
}

fun getDateStringWithOffset(date: String, days: Long, isForward: Boolean): String {
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


fun formatDate(year: String, month: String, day: String): String {
    val yearInt = year.toInt()
    val monthInt = month.toInt()
    val dayInt = day.toInt()

    val date = LocalDate.of(yearInt, monthInt, dayInt)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    return date.format(formatter)
}

fun isDateValid(dateFrom: String, dateTo: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val fromDate = LocalDate.parse(dateFrom, formatter)
    val toDate = LocalDate.parse(dateTo, formatter)
    return !fromDate.isAfter(toDate)
}

fun isDateInvalid(dateFrom: String, dateTo: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val fromDate = LocalDate.parse(dateFrom, formatter)
    val toDate = LocalDate.parse(dateTo, formatter)
    return fromDate.isAfter(toDate)
}