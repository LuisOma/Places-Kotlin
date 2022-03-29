package com.example.minutestest.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.minutestest.domain.model.Result
import com.example.minutestest.domain.repository.MapsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    var navController: NavController? = null
    val selectedLocation = MutableLiveData<Result>()
    val currentLocation = MutableLiveData<String>()
    var nearLocations: MutableLiveData<List<Result>> = MutableLiveData(null)
    val detailLocation = MutableLiveData<Result>()

    private val radius = 1000


    fun getUsers() {
        GlobalScope.launch {
            val res = MapsRepository.getList(currentLocation.value,radius,"AIzaSyBQxrRs9gALWm8krbuH4dMd0l9LIvNBWcQ")
            launch(Dispatchers.Main) {
                var result = res?.results?.toMutableList()
                result?.removeAt(0)
                nearLocations.value = result
            }
        }
    }
}