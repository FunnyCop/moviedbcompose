package com.logan.moviedbcompose.repo

import com.logan.moviedbcompose.model.Genre
import com.logan.moviedbcompose.model.Movie
import com.logan.moviedbcompose.model.MovieResponse
import com.logan.moviedbcompose.repo.remote.TMDBService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TMDBRepo @Inject constructor(
    private val tmdbService: TMDBService
) {

    suspend fun getPopularMovies(page: Int): MovieResponse? {

        val response = tmdbService.getPopularMovies(page)

        return response.body()

    }

    suspend fun getGenres(): List<Genre>? {

        val response = tmdbService.getGenres()

        return if (response.isSuccessful) response.body()?.genres else null

    }

}