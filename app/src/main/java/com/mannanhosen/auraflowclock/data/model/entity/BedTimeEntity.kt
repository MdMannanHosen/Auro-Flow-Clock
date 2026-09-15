package com.mannanhosen.auraflowclock.data.model.entity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mannanhosen.auraflowclock.data.model.Converters
import java.time.LocalTime

@Entity(
    tableName = "bedtime_table"
)
@RequiresApi(Build.VERSION_CODES.O)
@TypeConverters(Converters::class)
data  class BedTime  (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val cycle: Int,
    val localTime: LocalTime = LocalTime.MIDNIGHT,
    val minute: Int,
    val time : LocalTime

)

class InvalidBedTimeException(message: String) : Exception(message)
