package com.mannanhosen.auraflowclock.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timezone_table")

data class TimeZone (
    @PrimaryKey
    val uid : String,

    @ColumnInfo(name = "timezone")
    val timeZoneName : String,

   @ColumnInfo(name = "display_name")
    val displayName : String = "",

    @ColumnInfo(name = "is_selected")
    val isSelected : Boolean = false


)

