package com.marxelosj.kotlinflowsample.data.server

import com.marxelosj.kotlinflowsample.data.domain.Movie
import com.marxelosj.kotlinflowsample.data.domain.RemoteDataSource
import com.marxelosj.kotlinflowsample.data.toDomainMovie

class MovieDataSource(private val apiKey: String) : RemoteDataSource {

    override suspend fun getMovies(page: Int): List<Movie> =
        MovieService.SERVICE
            .listPopularMoviesAsync(apiKey, page)
            .results
            .map { it.toDomainMovie() }
}