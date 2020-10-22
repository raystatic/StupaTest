package com.example.stupatest.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stupatest.models.ShapeCoords
import com.example.stupatest.repositories.ShapeRepository
import kotlinx.coroutines.launch

class ShapeViewModel @ViewModelInject constructor(
    private val shapeRepository: ShapeRepository
) : ViewModel(){

    private val _shapes = MutableLiveData<List<ShapeCoords>>()

    val shapes:LiveData<List<ShapeCoords>> = shapeRepository.getAllShapes()

    fun deleteAllShapes() = viewModelScope.launch {
        shapeRepository.deleteAllShapes()
    }

    fun deleteShapeById(id:Int) = viewModelScope.launch {
        shapeRepository.deleteShapeById(id)
    }

    fun insertShape(shapeCoords: ShapeCoords) = viewModelScope.launch {
        shapeRepository.insertShape(shapeCoords)
    }

}