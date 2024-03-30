package com.rfid.hack277.dao

import com.rfid.hack277.model.Response
import retrofit2.Call


open interface Service {
    fun get(
        country: String,
        startYear: String,
        endYear: String,
        filter: String
    ): Call<List<Response>>
}