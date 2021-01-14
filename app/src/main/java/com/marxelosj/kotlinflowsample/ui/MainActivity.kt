package com.marxelosj.kotlinflowsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.marxelosj.kotlinflowsample.R
import com.marxelosj.kotlinflowsample.data.db.RoomDataSource
import com.marxelosj.kotlinflowsample.data.domain.MoviesRepository
import com.marxelosj.kotlinflowsample.data.server.MovieDataSource
import com.marxelosj.kotlinflowsample.databinding.ActivityMainBinding
import com.marxelosj.kotlinflowsample.ui.common.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            viewModel = getViewModel(::buildViewModel)
            val moviesAdapter = MoviesAdapter(lifecycleScope)

            lifecycleScope.collectFlow(viewModel.spinner) { progress.visible = it }
            lifecycleScope.collectFlow(viewModel.movies) { moviesAdapter.submitList(it)}
            lifecycleScope.collectFlow(recycler.lastVisibleEvents) {
                viewModel.notifyLastVisible(it)
            }

            recycler.adapter = moviesAdapter
        }
    }

    private fun buildViewModel() = MainViewModel(
        MoviesRepository(
            RoomDataSource(app.database),
            MovieDataSource(getString(R.string.api_key))
        )
    )
}