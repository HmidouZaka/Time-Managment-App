package com.theprojectbyzakariadeveloper.timemanagment.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
@Parcelize
@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(name = "data")
    var task:String,
    var date:String,
    var time:String,
    var category:String
): Parcelable
