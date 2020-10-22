package com.example.stupatest.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stupatest.R
import com.example.stupatest.viewmodels.ShapeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_chart.*

@AndroidEntryPoint
class ChartActivity : AppCompatActivity() {

    private val vm:ShapeViewModel by viewModels()
    private lateinit var chartAdapter: ChartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        btnCam.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        chartAdapter = ChartAdapter(this)
        chartRv.apply {
            layoutManager = LinearLayoutManager(this@ChartActivity)
            adapter = chartAdapter
        }
        subscribeToObservers()

    }

    private fun subscribeToObservers() {

        vm.shapes.observe(this, Observer {
            it?.let {
                chartAdapter.setData(it)
            }
        })

    }
}