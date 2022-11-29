package com.pierre.data.repository.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "positions")
data class DataPosition(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "square_x")
    val squareX : Float,
    @ColumnInfo(name = "square_y")
    val squareY : Float,
    @ColumnInfo(name = "touch_x")
    val touchX : Float,
    @ColumnInfo(name = "touch_y")
    val touchY : Float,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long
)