package com.example.btncounterapp.data_binding_view_binding_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyAppViewModel : ViewModel() {


    var fname = "Mohammed "
    var lname = "Elamin"
    var max = 100


    private val _likes = MutableLiveData(0)


//    var likes: Int = 0

    val likes: LiveData<Int> = _likes


    //     we need to observe likes
    fun increase() {
        _likes.value = _likes.value?.plus(1)

    }

}