package com.logan.moviedbcompose.repo.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.logan.moviedbcompose.model.GenreEntity

@Dao
interface GenreDao {

    @Query("SELECT * FROM genres")
    fun getGenres(): LiveData<List<GenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenres(genres: List<GenreEntity>)

}