package com.app.nursecalling.service

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "192.168.0.192/"

interface ApiService {

    @GET(value = "AX.htm")
    fun getEvents(@Query("A") channelId: String, @Query("B") password: String):Call<String>


}