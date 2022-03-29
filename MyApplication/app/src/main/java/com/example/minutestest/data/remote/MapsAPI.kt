package com.example.minutestest.data.remote

import com.example.minutestest.domain.model.ApiPlacesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MapsAPI {
    @Headers(
        "Content-Type: application/json")
    @GET("json")
    suspend fun getInfo(@Query("location") location: String?,
                        @Query("radius") radius: Int,
                        @Query("key") key: String
    ): Response<ApiPlacesResponse>
}
