package com.example.stupatest.repositories

import com.example.stupatest.db.UserDao
import com.example.stupatest.models.User
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val userDao: UserDao
){

    suspend fun insertUser(user: User) = userDao.insertUser(user)

    fun geUser() = userDao.getUser()

    suspend fun deleteUser() = userDao.deleteUser()

}