package com.logan.moviedbcompose.work

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.asLiveData
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.logan.moviedbcompose.repo.LocalGenreRepo
import com.logan.moviedbcompose.repo.LocalMovieRepo
import com.logan.moviedbcompose.repo.TMDBRepo
import com.logan.moviedbcompose.util.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.concurrent.atomic.AtomicInteger

@HiltWorker
class TMDBSync @AssistedInject constructor(

    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val tmdbRepo: TMDBRepo,
    private val localGenreRepo: LocalGenreRepo,
    private val localMovieRepo: LocalMovieRepo

) : CoroutineWorker(context, params) {

    private val totalPagesKey = intPreferencesKey(PAGES_PREFERENCES_KEY)
    private val currentPageKey = intPreferencesKey(CURRENT_PAGE_PREFERENCES_KEY)
    private val genresSyncedKey = booleanPreferencesKey(GENRES_SYNCED_KEY)
    private val syncCompletedKey = booleanPreferencesKey(SYNC_COMPLETED_KEY)

    private val totalPages = context.datastore.data.map { it[totalPagesKey] }
    private val currentPage = context.datastore.data.map { it[currentPageKey] }
    private val genresSynced = context.datastore.data.map{ it[genresSyncedKey] }
    private val syncCompleted = context.datastore.data.map { it[syncCompletedKey] }

    private suspend fun setTotalPages(pages: Int) =
        applicationContext.datastore.edit { it[totalPagesKey] = pages }

    private suspend fun setCurrentPage(page: Int) =
        applicationContext.datastore.edit { it[currentPageKey] = page }

    private suspend fun setGenresSynced(completed: Boolean) =
        applicationContext.datastore.edit { it[genresSyncedKey] = completed }

    private suspend fun setSyncCompleted(completed: Boolean) =
        applicationContext.datastore.edit { it[syncCompletedKey] = completed }

    private val lifecycleOwner = object : LifecycleOwner {

        private lateinit var lifeCycleRegistry: LifecycleRegistry

        fun destroy() { if (this::lifeCycleRegistry.isInitialized) {

            lifeCycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            lifeCycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
            lifeCycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

        } }

        override fun getLifecycle(): LifecycleRegistry {

            if (!this::lifeCycleRegistry.isInitialized) {

                lifeCycleRegistry = LifecycleRegistry(this)
                lifeCycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)

            }

            return lifeCycleRegistry

        }

    }

    @ObsoleteCoroutinesApi
    private suspend fun initValues() = coroutineScope {

        val totalPages = AtomicInteger(0)
        val currentPage = AtomicInteger(0)
        val scope = CoroutineScope(newSingleThreadContext("TMDB Sync"))

        scope.launch(Dispatchers.Main) {

            this@TMDBSync.totalPages.asLiveData(scope.coroutineContext).observeUntilNotNull(lifecycleOwner) {
                Log.d("DEBUG_INFO", "Total Pages: $it")
            }

            this@TMDBSync.currentPage.asLiveData(scope.coroutineContext).observe(lifecycleOwner) {
                Log.d("DEBUG_INFO", "Current Page: $it")
            }

            genresSynced.asLiveData(scope.coroutineContext).observeUntilNotNull(lifecycleOwner) {
                Log.d("DEBUG_INFO", "Genres Synced: $it")
            }

            syncCompleted.asLiveData(scope.coroutineContext).observeUntilNotNull(lifecycleOwner) {
                Log.d("DEBUG_INFO", "Sync Completed: $it")
            }

        }

        scope.launch(Dispatchers.IO) {

            if (this@TMDBSync.totalPages.first() == null) {

                setSyncCompleted(false)

                tmdbRepo.getPopularMovies(1)?.let {

                    totalPages.set(it.totalPages)
                    setTotalPages(totalPages.get())
                    currentPage.incrementAndGet()
                    setCurrentPage(currentPage.get())
                    localMovieRepo.addMovies(it.results.toEntityList())

                }

                if (genresSynced.first() != true)
                    tmdbRepo.getGenres()?.let {

                        localGenreRepo.addGenres(it.toEntityList())
                        setGenresSynced(true)

                    }

            } else {

                this@TMDBSync.totalPages.first()?.let { totalPages.set(it) }
                this@TMDBSync.currentPage.first()?.let { currentPage.set(it) }

            }

            if (totalPages.get() == 0 && currentPage.get() == 0)
                Result.failure()

            else while (totalPages.get() != currentPage.get()) {

                val result = tmdbRepo.getPopularMovies(currentPage.get())

                if (result != null) {

                    localMovieRepo.addMovies(result.results.toEntityList())
                    currentPage.incrementAndGet()
                    setCurrentPage(currentPage.get())

                }

            }

        }

        scope.launch(Dispatchers.Main) {

            setSyncCompleted(true)
            lifecycleOwner.destroy()

        }

        Result.success()

    }

    @ObsoleteCoroutinesApi
    override suspend fun doWork(): Result {

        if (syncCompleted.first() == null || syncCompleted.first() == false)
            return initValues()

        return Result.success()

    }

}