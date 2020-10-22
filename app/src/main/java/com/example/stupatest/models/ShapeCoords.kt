package com.example.stupatest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shape_coords")
data class ShapeCoords(

    @ColumnInfo(name = "shapes")
    val shapes:List<StarData>

){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int = 0
}