package com.pierre.data.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pierre.data.repository.models.DataPosition

class Converters {

    @TypeConverter
    fun listToJson(value: List<DataPosition>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<DataPosition>::class.java).toList()
}