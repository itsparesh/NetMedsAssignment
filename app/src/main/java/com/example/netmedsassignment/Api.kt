package com.example.netmedsassignment

import retrofit2.Call
import retrofit2.http.GET

internal interface Api {
    @get:GET("api/medicines")
    val medicines: Call<List<Medicines>>

    companion object {
        const val BASE_URL = "https://6082d0095dbd2c001757a8de.mockapi.io/"
    }
}