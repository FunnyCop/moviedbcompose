package com.logan.moviedbcompose

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.logan.moviedbcompose.work.TMDBSyncWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: TMDBSyncWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}