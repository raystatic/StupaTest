package com.example.stupatest.ui

import android.content.Context
import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stupatest.R
import com.example.stupatest.models.ShapeCoords
import kotlinx.android.synthetic.main.chart_item.view.*


class ChartAdapter(var context:Context) : RecyclerView.Adapter<ChartAdapter.ChartViewHolder>(){

    private var data:List<ShapeCoords>?=null

    fun setData(list:List<ShapeCoords>){
        data = list
        notifyDataSetChanged()
    }

    inner class ChartViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder  =
        ChartViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.chart_item, parent, false)
        )

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {

        val item = data?.get(position)

        val shapes = item?.shapes
        val x1 = shapes?.get(0)?.x
        val y1 = shapes?.get(0)?.y

        val x2 = shapes?.get(1)?.x
        val y2 = shapes?.get(1)?.y

        val x3 = shapes?.get(2)?.x
        val y3 = shapes?.get(2)?.y

        val x4 = shapes?.get(3)?.x
        val y4 = shapes?.get(3)?.y

        holder.itemView.chartImg.apply {
            X1 = x1!!
            Y1 = y1!!
            X2 = x2!!
            Y2 = y2!!
            X3 = x3!!
            Y3 = y3!!
            X4 = x4!!
            Y4 = y4!!
        }

    }
}