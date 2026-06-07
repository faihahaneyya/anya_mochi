package com.example.anya_mochi.api

import com.example.anya_mochi.Home.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    fun getTopBerita(
        @Query("country") country: String = "id",
        @Query("apiKey") apiKey: String = "4ba88c1cfa2d48bfbc89552cb647a7b1"
    ): Call<NewsResponse>
}