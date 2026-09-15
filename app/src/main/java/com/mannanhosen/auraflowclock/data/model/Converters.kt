package com.mannanhosen.auraflowclock.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalTime
import java.util.Date

class Converters {

    // রং-এর লিস্টকে সংখ্যার লিস্টে বদলে, তারপর লেখায় (String) বদলাই
    @TypeConverter
    fun fromColorList(colors: List<Color>): String {
        val colorLongs = colors.map { it.value.toLong() }
        return Gson().toJson(colorLongs)
    }

    // লেখা থেকে আবার সংখ্যা বের করে, সেই সংখ্যা দিয়ে আবার রং বানাই
    @TypeConverter
    fun toColorList(data: String): List<Color> {
        val type = object : TypeToken<List<Long>>() {}.type
        val colorLongs: List<Long> = Gson().fromJson(data, type)
        return colorLongs.map { Color(it.toULong()) }
    }

    // ---- Date <-> Long (timestamp) ----
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun fromLocalTime(time: LocalTime?): String? {
        return time?.toString() // e.g. "14:30" or "14:30:00"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalTime(timeString: String?): LocalTime? {
        return timeString?.let { LocalTime.parse(it) }
    }


}