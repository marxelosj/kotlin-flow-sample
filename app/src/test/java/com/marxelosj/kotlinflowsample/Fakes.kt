package com.marxelosj.kotlinflowsample

import com.marxelosj.kotlinflowsample.data.domain.LocalDataSource
import com.marxelosj.kotlinflowsample.data.domain.Movie
import com.marxelosj.kotlinflowsample.data.domain.RemoteDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

val fakeMovies = listOf(
    Movie(1, "Title 1", "poster1", 7.0),
    Movie(2, "Title 2", "poster2", 6.0),
    Movie(3, "Title 3", "poster3", 5.5),
    Movie(4, "Title 4", "poster4", 4.2),
)

class FakeLocalDataSource : LocalDataSource {

    private val movies = mutableListOf<Movie>()

    override suspend fun size(): Int = movies.size

    override suspend fun saveMovies(movies: List<Movie>) {
        this.movies += movies
    }

    override fun getMovies(): Flow<List<Movie>> = flowOf(movies)

}

class FakeRemoteDataSource(
    private val movies: List<Movie> = emptyList(),
    private val delay: Long = 0
) : RemoteDataSource {

    override suspend fun getMovies(page: Int): List<Movie> {
        delay(delay)
        return movies
    }
}
