package com.marxelosj.kotlinflowsample.data.db

import com.marxelosj.kotlinflowsample.data.domain.LocalDataSource
import com.marxelosj.kotlinflowsample.data.domain.Movie
import com.marxelosj.kotlinflowsample.data.toDomainMovie
import com.marxelosj.kotlinflowsample.data.toRoomMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomDataSource(database: MovieDatabase) : LocalDataSource {

    private val movieDao = database.movieDao()

    override suspend fun size(): Int = movieDao.movieCount()

    override suspend fun saveMovies(movies: List<Movie>) {
        movieDao.insertMovies(movies.map { it.toRoomMovie() })
    }

    override fun getMovies(): Flow<List<Movie>> =
        movieDao.getAll().map { movies -> movies.map { it.toDomainMovie() } }
}