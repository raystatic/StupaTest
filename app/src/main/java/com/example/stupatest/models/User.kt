package com.example.stupatest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(

    @ColumnInfo(name = "name")
    val name:String,

    @ColumnInfo(name = "email")
    @PrimaryKey
    val email:String
)