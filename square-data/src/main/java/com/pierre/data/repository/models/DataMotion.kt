package com.pierre.data.repository.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motions")
data class DataMotion(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "positions")
    val positions: List<DataPosition>,
    @ColumnInfo(name = "startTime")
    val startTime: Long,
    @ColumnInfo(name = "endTime")
    val endTime: Long,
    @ColumnInfo(name = "exceeded")
    val exceeded: Boolean
)