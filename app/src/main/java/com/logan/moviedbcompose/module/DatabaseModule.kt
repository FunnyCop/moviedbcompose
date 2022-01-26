package com.logan.moviedbcompose.module

import android.content.Context
import androidx.room.Room
import com.logan.moviedbcompose.repo.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(

        @ApplicationContext context: Context

    ) = Room.databaseBuilder(

        context,
        LocalDatabase::class.java,
        "movie_and_genre_database"

    ).build()

    @Singleton
    @Provides
    fun provideMovieDao(localDatabase: LocalDatabase) = localDatabase.movieDao()

    @Singleton
    @Provides
    fun provideGenreDao(localDatabase: LocalDatabase) = localDatabase.genreDao()

}