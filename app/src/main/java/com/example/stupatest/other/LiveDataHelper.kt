package com.example.stupatest.other

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.stupatest.models.StarData

class LiveDataHelper {

    private val _starCount = MediatorLiveData<List<StarData>>()

    val starCount:LiveData<List<StarData>>
        get() = _starCount

    companion object{
        var liveDataHelper: LiveDataHelper?=null

        @Synchronized
        fun getInstance(): LiveDataHelper?{
            if (liveDataHelper == null) liveDataHelper =
                LiveDataHelper()

            return liveDataHelper
        }
    }

    fun setStarCount(starData: MutableList<StarData>) {
        _starCount.postValue(starData)
    }

}