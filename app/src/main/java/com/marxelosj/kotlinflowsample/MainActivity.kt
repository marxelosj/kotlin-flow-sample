package com.marxelosj.kotlinflowsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.marxelosj.kotlinflowsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private var mItems: List<String> = emptyList()

    private lateinit var mBinding: ActivityMainBinding
    private val observer = { items: List<String> -> mItems = items }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

}