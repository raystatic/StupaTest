package com.example.stupatest.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stupatest.models.ShapeCoords

@Dao
interface ShapeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShape(shapeCoords: ShapeCoords)

    @Query("DELETE from shape_coords")
    suspend fun deleteAllShapes()

    @Query("DELETE from shape_coords WHERE id=:id")
    suspend fun deleteShape(id:Int)

    @Query("SELECT * from shape_coords ORDER BY id DESC")
    fun getShapes(): LiveData<List<ShapeCoords>>
}