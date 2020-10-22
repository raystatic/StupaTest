package com.example.stupatest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.stupatest.models.ShapeCoords
import com.example.stupatest.models.User

@Database(
    entities = [User::class, ShapeCoords::class],
    version = 1
)
@TypeConverters(
    Converters::class
)
abstract class StupdDB: RoomDatabase(){

    abstract fun getUserDao():UserDao
    abstract fun getShapeDao():ShapeDao

}