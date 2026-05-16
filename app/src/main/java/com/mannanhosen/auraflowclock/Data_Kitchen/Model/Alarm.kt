package com.mannanhosen.auraflowclock.Data_Kitchen.Model

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.format.DateTimeFormatter

@Entity(
    tableName = "Alarm_List_Table",
    indices = [
        Index(
            value = ["hour", "minute", "title", "isRecurring", "daysSelectedGson"],
            unique = true
        )
    ]
)
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var hour: String = "00",
    var minute: String = "00",
    var title: String = "",
    var isScheduled: Boolean = true,
    var isRecurring: Boolean = true,

    // সপ্তাহের ৭টি দিনের ইউনিক ম্যাপ (Default সব false)
    var daysSelectedGson: String = Gson().toJson(
        mapOf(
            "Sat" to false,
            "Sun" to false,
            "Mon" to false,
            "Tue" to false,
            "Wed" to false,
            "Thu" to false,
            "Fri" to false
        )
    )
) {
    // Room ডাটাবেজ যেন এই ফরম্যাটারটিকে কলাম না বানায়, তাই @Ignore দেওয়া হলো
    @SuppressLint("NewApi")
    @Ignore
    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd")

    // Gson থেকে ম্যাপে কনভার্ট করার গেটার প্রোপার্টি
    val daysSelected: Map<String, Boolean>
        get() = Gson().fromJson(daysSelectedGson, object : TypeToken<Map<String, Boolean>>() {}.type)

    // ম্যাপ থেকে Gson স্ট্রিংয়ে কনভার্ট করার ফাংশন
    fun setDaysSelected(daysSelected: Map<String, Boolean>) {
        this.daysSelectedGson = Gson().toJson(daysSelected)
    }
}