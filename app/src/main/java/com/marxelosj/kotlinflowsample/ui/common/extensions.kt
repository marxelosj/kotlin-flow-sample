package com.marxelosj.kotlinflowsample.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marxelosj.kotlinflowsample.MovieApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> T): T {

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this, vmFactory).get()
}

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

val Context.app: MovieApp
    get() = applicationContext as MovieApp

fun <T> CoroutineScope.collectFlow(flow: Flow<T>, body: suspend (T) -> Unit) {
    flow.onEach { body(it) }
            .launchIn(this)
}

@ExperimentalCoroutinesApi
val View.onClickEvents: Flow<View>
    get() = callbackFlow {
        val onClickListener = View.OnClickListener { offer(it) }
        setOnClickListener(onClickListener)
        awaitClose { setOnClickListener(null) }
    }.conflate()

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@ExperimentalCoroutinesApi
val RecyclerView.lastVisibleEvents: Flow<Int>
    get() = callbackFlow<Int> {
        val lm = layoutManager as GridLayoutManager

        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                offer(lm.findLastVisibleItemPosition())
            }
        }
        addOnScrollListener(listener)
        awaitClose { removeOnScrollListener(listener) }
    }.conflate()

@SuppressLint("SimpleDateFormat")
fun TextView.getDate(source: String, fromPattern: String, toPattern: String) {
    try {
        val date: Date = SimpleDateFormat(fromPattern).parse(source)
        text = SimpleDateFormat(toPattern, Locale.getDefault()).format(date)
    } catch (e: Exception){
        text = "-"
    }

}