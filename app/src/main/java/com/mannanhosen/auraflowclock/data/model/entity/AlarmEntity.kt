package com.mannanhosen.auraflowclock.data.model.entity
import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mannanhosen.auraflowclock.data.model.ChallengeType
import com.mannanhosen.auraflowclock.ui.theme.Caviar
import com.mannanhosen.auraflowclock.ui.theme.Gray
import com.mannanhosen.auraflowclock.ui.theme.Onyx
import com.mannanhosen.auraflowclock.ui.theme.Pink500
import com.mannanhosen.auraflowclock.ui.theme.SeaGreen
import java.time.format.DateTimeFormatter
import java.util.Date

@Entity(
    tableName = "Alarm_List_Table",
    indices = [
        Index(
            value = ["hour", "minute", "title", "isRecurring", "daysSelectedGson", "weekDays", "selectedCalenderButton"],
            unique = true
        )
    ]
)
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var hour: String = "00",
    var minute: String = "00",
    var period: String,
    var title: String,
    var color: List<Color>,
    var weekDays: String,
    val ringtoneName: String = "Default",
    val isEnabled: Boolean = true,

    var vibrationPattern: String = "Short Pulse",
    var selectedCalenderButton: Boolean = true,
    var isScheduled: Boolean = true,
    var isRecurring: Boolean = true,
    var isRepeating: Boolean = true,
    val qrTargetValue : String? = null,
    val mathDifficulty : Int = 1,
    val challengeType: ChallengeType = ChallengeType.DEFAULT,
    var reapeatDays: String = "",
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
    ),
    val date: Date?,

    // ✅ নতুন যোগ করা হলো — Ringtone, Vibration, Snooze
    var ringtoneTitle: String = "Default",
    var ringtoneUri: String? = null,
    var isVibrationEnabled: Boolean = true,
    var snoozeDuration: Int = 5,
    var snoozeCount: Int = 3
) {
    @SuppressLint("NewApi")
    @Ignore
    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd")

    val daysSelected: Map<String, Boolean>
        get() = Gson().fromJson(daysSelectedGson, object : TypeToken<Map<String, Boolean>>() {}.type)

    fun setDaysSelected(daysSelected: Map<String, Boolean>) {
        this.daysSelectedGson = Gson().toJson(daysSelected)
    }

    fun getDaysSelectedDisplay(): String {
        val selectedDays = daysSelected.filterValues { it }.keys

        return when {
            selectedDays.isEmpty() -> "Only once"
            selectedDays.size == 7 -> "Every day"
            selectedDays == setOf("Mon", "Tue", "Wed", "Thu", "Fri") -> "Weekdays"
            selectedDays == setOf("Sat", "Sun") -> "Weekends"
            else -> selectedDays.joinToString(", ")
        }
    }

    companion object {
        val toggleButtonColor = listOf(Pink500, Onyx, Gray)
        val containerColor = listOf(SeaGreen, Caviar)
    }
}

class InvalidAlarmException(message: String) : Exception(message)