package com.marxelosj.kotlinflowsample.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.marxelosj.kotlinflowsample.R
import com.marxelosj.kotlinflowsample.data.domain.Movie
import com.marxelosj.kotlinflowsample.databinding.ItemMovieBinding
import com.marxelosj.kotlinflowsample.ui.common.collectFlow
import com.marxelosj.kotlinflowsample.ui.common.onClickEvents
import com.marxelosj.kotlinflowsample.ui.common.toast
import com.marxelosj.kotlinflowsample.ui.common.visible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoroutinesApi
class MoviesAdapter(private val scope: CoroutineScope) :
        ListAdapter<Movie, MoviesAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
        scope.collectFlow(itemView.onClickEvents) {
            itemView.context.toast(item.title)
        }
    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemMovieBinding.bind(itemView)

        fun bind(item: Movie) = with(binding) {
            movieTitle.text = item.title
            movieReleaseDate.text = getSimpleDateFormat("MMM d, yyyy", item.releaseDate)
            Glide.with(movieCover.context)
                    .load("https://image.tmdb.org/t/p/w185${item.posterPath}")
                    .transition(withCrossFade())
                    .listener(object : RequestListener<Drawable> {
                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            progressBar.visible = false
                            return false
                        }

                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            TODO("Not yet implemented")
                        }

                    })
                    .into(movieCover)
        }

        @SuppressLint("SimpleDateFormat")
        fun getSimpleDateFormat(format: String, releaseDate: String): String {
            val date: Date = SimpleDateFormat("yyyy-MM-dd").parse(releaseDate)
            return SimpleDateFormat(format, Locale.getDefault()).format(date)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}