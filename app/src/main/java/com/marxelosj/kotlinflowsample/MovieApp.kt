package com.marxelosj.kotlinflowsample

import android.app.Application
import androidx.room.Room
import com.marxelosj.kotlinflowsample.data.db.MovieDatabase

class MovieApp : Application() {
    lateinit var database: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            MovieDatabase::class.java, "movie_db"
        ).build()
    }
}