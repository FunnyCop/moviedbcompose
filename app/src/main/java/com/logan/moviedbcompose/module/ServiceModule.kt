package com.logan.moviedbcompose.module

import com.logan.moviedbcompose.repo.remote.TMDBService
import com.logan.moviedbcompose.util.TMDB_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun provideTMDBService(): TMDBService = Retrofit.Builder()
        .baseUrl(TMDB_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(TMDBService::class.java)

}