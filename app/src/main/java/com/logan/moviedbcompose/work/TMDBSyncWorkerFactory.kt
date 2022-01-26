package com.logan.moviedbcompose.work

import android.content.Context
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.logan.moviedbcompose.repo.LocalGenreRepo
import com.logan.moviedbcompose.repo.LocalMovieRepo
import com.logan.moviedbcompose.repo.TMDBRepo
import javax.inject.Inject

class TMDBSyncWorkerFactory @Inject constructor(

    private val tmdbRepo: TMDBRepo,
    private val localGenreRepo: LocalGenreRepo,
    private val localMovieRepo: LocalMovieRepo

) : WorkerFactory() {

    override fun createWorker(

        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters

    ) = TMDBSync(appContext, workerParameters, tmdbRepo, localGenreRepo, localMovieRepo)

}