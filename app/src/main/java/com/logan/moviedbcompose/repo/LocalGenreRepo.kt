package com.logan.moviedbcompose.repo

import com.logan.moviedbcompose.model.GenreEntity
import com.logan.moviedbcompose.repo.local.GenreDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalGenreRepo @Inject constructor(

    private val genreDao: GenreDao

) {

    val genres = genreDao.getGenres()

    suspend fun addGenres(genres: List<GenreEntity>) = genreDao.addGenres(genres)

}