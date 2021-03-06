package com.marxelosj.kotlinflowsample.data.domain

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val voteAverage: Double,
    val releaseDate: String
)