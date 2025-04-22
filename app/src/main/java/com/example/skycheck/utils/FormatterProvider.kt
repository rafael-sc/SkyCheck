package com.example.skycheck.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatTimestampToTime(timestamp: Long): String {
//    val date = Date(timestamp)
//    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
//    return format.format(date)

    val localDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(timestamp),
        ZoneId.systemDefault() // Usa o fuso horário do sistema
    )
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return localDateTime.format(formatter)
}

fun convertKelvinToCelsius(temperature: Double): Int {
    val kelvinBaseUnit = 273.15
    return (temperature - kelvinBaseUnit).toInt()
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatCurrentDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM", Locale("pt", "BR"))
//    return DateTimeFormatter.ofPattern("dd MMM", Locale("pt", "BR")).format(date)
    return date.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDayOfWeek(date: LocalDate): String {
    return when (date.dayOfWeek!!) {
        DayOfWeek.MONDAY -> "Seg"
        DayOfWeek.TUESDAY -> "Ter"
        DayOfWeek.WEDNESDAY -> "Qua"
        DayOfWeek.THURSDAY -> "Qui"
        DayOfWeek.FRIDAY -> "Sex"
        DayOfWeek.SATURDAY -> "Sab"
        DayOfWeek.SUNDAY -> "Dom"
    }
}

fun capitalizeSentence(sentence: String): String {
    return sentence.replaceFirstChar { it.uppercase() }
}
