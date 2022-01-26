package com.logan.moviedbcompose.util

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.logan.moviedbcompose.model.Genre
import com.logan.moviedbcompose.model.GenreEntity
import com.logan.moviedbcompose.model.Movie
import com.logan.moviedbcompose.model.MovieEntity

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {

    observe(lifecycleOwner, object : Observer<T> { override fun onChanged(t: T) {

        observer.onChanged(t)
        removeObserver(this)

    } })

}

fun <T> LiveData<T>.observeUntilNotNull(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {

    observe(lifecycleOwner, object : Observer<T> { override fun onChanged(t: T) {

        observer.onChanged(t)

        if (t != null)
            removeObserver(this)

    } })

}

fun <T> LiveData<List<T>>.observeUntilNotEmpty(lifecycleOwner: LifecycleOwner, observer: Observer<List<T>>) {

    observe(lifecycleOwner, object : Observer<List<T>> { override fun onChanged(t: List<T>) {

        observer.onChanged(t)

        if (t.isNotEmpty())
            removeObserver(this)

    } })

}

inline fun <T> LiveData<T>.observeUntil(

    lifecycleOwner: LifecycleOwner,
    observer: Observer<T>,
    crossinline predicate: (T) -> Boolean

) { observe(lifecycleOwner, object : Observer<T> { override fun onChanged(t: T) {

    if (predicate(t)) {

        observer.onChanged(t)
        removeObserver(this)

    }

} }) }

inline fun <T> LiveData<T>.observeWhile(

    lifecycleOwner: LifecycleOwner,
    observer: Observer<T>,
    crossinline predicate: (T) -> Boolean,
    crossinline callback: () -> Unit

) { observe(lifecycleOwner, object : Observer<T> { override fun onChanged(t: T) {

    observer.onChanged(t)

    if (!predicate(t)) {

        removeObserver(this)
        callback()

    }

} }) }

fun Movie.toEntity() = MovieEntity(

    posterPath,
    adult,
    overview,
    releaseDate,
    genreIds,
    id,
    originalTitle,
    originalLanguage,
    title,
    backdropPath,
    popularity,
    voteCount,
    video,
    voteAverage

)

fun List<Movie>.toEntityList() = map { it.toEntity() }

fun Genre.toEntity() = GenreEntity(id, name)

@JvmName("genreListToGenreEntityList")
fun List<Genre>.toEntityList() = map { it.toEntity() }

val Context.datastore by preferencesDataStore(name = SYNC_DATASTORE_NAME)