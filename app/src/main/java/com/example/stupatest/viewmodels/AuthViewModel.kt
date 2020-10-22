package com.example.stupatest.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stupatest.models.User
import com.example.stupatest.repositories.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository
):ViewModel(){

    val user:LiveData<User> = authRepository.geUser()

    fun insertUser(user: User) = viewModelScope.launch {
        authRepository.insertUser(user)
    }

    fun deleteUser() = viewModelScope.launch {
        authRepository.deleteUser()
    }

}