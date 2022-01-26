package com.logan.moviedbcompose.repo.remote

import com.logan.moviedbcompose.model.GenreResponse
import com.logan.moviedbcompose.model.MovieResponse
import com.logan.moviedbcompose.util.TMDB_API_KEY_V3
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {

    @GET("movie/popular?api_key=$TMDB_API_KEY_V3&language=en-US")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MovieResponse>

    @GET("genre/movie/list?api_key=$TMDB_API_KEY_V3&language=en-US")
    suspend fun getGenres(): Response<GenreResponse>

}