package com.example.minutestest.domain.repository

import com.example.minutestest.data.remote.RetrofitClient
import com.example.minutestest.data.remote.MapsAPI
import com.example.minutestest.domain.model.ApiPlacesResponse
import kotlinx.coroutines.coroutineScope

object MapsRepository {

    suspend fun getList(location: String?, radius: Int, key: String): ApiPlacesResponse? = coroutineScope {
        var res: ApiPlacesResponse? = null

        try {
            val retrofit = RetrofitClient().getRetrofitApi(RetrofitClient.BASEURL)
            val userService = retrofit.create(MapsAPI::class.java)

            val result = userService.getInfo(location,radius, key)

            if (result.isSuccessful) {
                res = result.body()!!
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        res
    }

}