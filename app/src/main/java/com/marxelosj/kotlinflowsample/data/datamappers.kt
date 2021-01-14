package com.marxelosj.kotlinflowsample.data

import com.marxelosj.kotlinflowsample.data.domain.Movie
import com.marxelosj.kotlinflowsample.data.db.Movie as RoomMovie
import com.marxelosj.kotlinflowsample.data.server.Movie as ServerMovie

fun ServerMovie.toDomainMovie(): Movie =
    Movie(
        0,
        title,
        posterPath,
        voteAverage,
    )

fun Movie.toRoomMovie(): RoomMovie =
    RoomMovie(
        id,
        title,
        posterPath,
        voteAverage,
    )

fun RoomMovie.toDomainMovie(): Movie = Movie(
    id,
    title,
    posterPath,
    voteAverage,
)