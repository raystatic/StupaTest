package com.example.stupatest.repositories

import com.example.stupatest.db.ShapeDao
import com.example.stupatest.models.ShapeCoords
import javax.inject.Inject

class ShapeRepository @Inject constructor(
    val shapeDao: ShapeDao
){

    suspend fun insertShape(shapeCoords: ShapeCoords) = shapeDao.insertShape(shapeCoords)

    suspend fun deleteAllShapes() = shapeDao.deleteAllShapes()

    suspend fun deleteShapeById(id:Int) = shapeDao.deleteShape(id)

    fun getAllShapes() = shapeDao.getShapes()

}