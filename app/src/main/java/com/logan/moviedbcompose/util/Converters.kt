package com.logan.moviedbcompose.util

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Singleton

@Singleton
class Converters {

    private val moshi by lazy { Moshi.Builder().build() }

    private val intListAdapter by lazy { moshi.adapter<List<Int>>(

        Types.newParameterizedType(List::class.java, Int::class.javaObjectType)

    ) }

    @TypeConverter
    fun intListToJson(value: List<Int>): String = intListAdapter.toJson(value)

    @TypeConverter
    fun jsonToIntList(value: String) = intListAdapter.fromJson(value)

}