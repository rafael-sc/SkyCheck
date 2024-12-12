package com.example.skycheck.utils.functions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatTimestampToTime(timestamp: Long): String {
    val localTime = Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalTime()
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return localTime.format(formatter)
}

fun convertKelvinToCelsius(temperature: Double): Double {
    val kelvinBaseUnit = 273.15
    return (temperature + kelvinBaseUnit) - kelvinBaseUnit
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatCurrentDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM", Locale("pt", "BR"))
    return date.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDayOfWeek(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("ddd", Locale("pt", "BR"))
    return capitalizeSentence(date.format(formatter))
}

fun capitalizeSentence(sentence: String): String {
    return sentence.replaceFirstChar { it.uppercase() }
}
