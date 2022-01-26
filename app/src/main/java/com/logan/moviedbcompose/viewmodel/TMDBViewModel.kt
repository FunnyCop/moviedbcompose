package com.logan.moviedbcompose.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logan.moviedbcompose.model.GenreEntity
import com.logan.moviedbcompose.model.Movie
import com.logan.moviedbcompose.model.MovieEntity
import com.logan.moviedbcompose.repo.LocalGenreRepo
import com.logan.moviedbcompose.repo.LocalMovieRepo
import com.logan.moviedbcompose.repo.TMDBRepo
import com.logan.moviedbcompose.util.toEntityList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.broadcast
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TMDBViewModel @Inject constructor(
    private val tmdbRepo: TMDBRepo,
    private val localMovieRepo: LocalMovieRepo,
    private val localGenreRepo: LocalGenreRepo
) : ViewModel() {

    private val _movies = MutableLiveData<List<MovieEntity>?>(null)
    val movies: LiveData<List<MovieEntity>?> get() = _movies

    private val _genres = MutableLiveData<List<GenreEntity>?>(null)
    val genres: LiveData<List<GenreEntity>?> get() = _genres

    private var page = 1
    private var pages = 0

    fun getMovies() = viewModelScope.launch(Dispatchers.IO) {

        if (localMovieRepo.movies.value.isNullOrEmpty()) {

            tmdbRepo.getPopularMovies(page).also { if (it != null) {

                pages = it.totalPages
                addMovies(it.results)
                page++

            } }

            while (page <= pages) { tmdbRepo.getPopularMovies(page).also {

                if (it != null) {

                    addMovies(it.results)
                    page++

                }

            } }

        }

    }

    private fun addMovies(movies: List<Movie>) {

//        val movieEntit = movies.toEntityList()
//
//        localMovieRepo.addMovies(movieList)

    }

}