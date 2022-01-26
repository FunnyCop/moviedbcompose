package com.logan.moviedbcompose.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreResponse(

    val genres: List<Genre>

)
