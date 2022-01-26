package com.logan.moviedbcompose.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.logan.moviedbcompose.model.GenreEntity
import com.logan.moviedbcompose.model.MovieEntity
import com.logan.moviedbcompose.util.Converters

@Database(entities = [MovieEntity::class, GenreEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

}