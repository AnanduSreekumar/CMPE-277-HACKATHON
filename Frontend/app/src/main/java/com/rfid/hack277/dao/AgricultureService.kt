package com.rfid.hack277.dao

import com.rfid.hack277.model.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AgricultureService: Service {
    @GET("agriculture/data")
    override fun get(
        @Query("country") country: String,
        @Query("startYear") startYear: String,
        @Query("endYear") endYear: String,
        @Query("filter") filter: String
    ): Call<List<Response>>
}