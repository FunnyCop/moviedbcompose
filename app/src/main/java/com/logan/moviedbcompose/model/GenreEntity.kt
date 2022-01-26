package com.logan.moviedbcompose.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreEntity(

    @PrimaryKey val id: Int,
    val name: String

)
