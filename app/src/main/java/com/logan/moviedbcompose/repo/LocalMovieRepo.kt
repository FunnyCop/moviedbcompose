package com.logan.moviedbcompose.repo

import com.logan.moviedbcompose.model.MovieEntity
import com.logan.moviedbcompose.repo.local.MovieDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalMovieRepo @Inject constructor(

    private val movieDao: MovieDao

) {

    val movies = movieDao.getAllMovies()

    suspend fun addMovies(movies: List<MovieEntity>) = movieDao.addMovies(movies)

}