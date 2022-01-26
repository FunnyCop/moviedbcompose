package com.logan.moviedbcompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.logan.moviedbcompose.ui.composable.MainContentHost
import com.logan.moviedbcompose.ui.theme.MovieDBComposeTheme
import com.logan.moviedbcompose.work.TMDBSync
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        WorkManager.getInstance(applicationContext).enqueue(OneTimeWorkRequestBuilder<TMDBSync>().build())
        setContent { MovieDBComposeTheme { MainContentHost() } }

    }

}