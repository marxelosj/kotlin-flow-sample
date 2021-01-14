package com.marxelosj.kotlinflowsample.data.server

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun listPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MovieResponse
}