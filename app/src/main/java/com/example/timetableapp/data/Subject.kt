
package com.example.timetableapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat


@Entity
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val subjectName: String,
    @ColumnInfo(name = "startTime")
    val subjectStartTime: String,
    @ColumnInfo(name = "endTime")
    val subjectEndTime: String,
)