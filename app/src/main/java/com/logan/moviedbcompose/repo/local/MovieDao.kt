package com.logan.moviedbcompose.repo.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.logan.moviedbcompose.model.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieEntity>)

}