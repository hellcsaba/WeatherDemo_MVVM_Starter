package hu.bme.aut.weatherdemo.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    const val MainServer = "https://api.openweathermap.org/"

    val retrofitClient: Retrofit.Builder by lazy{
        Retrofit.Builder()
            .baseUrl(MainServer)
            .addConverterFactory(MoshiConverterFactory.create())
    }

    val apiInterface: WeatherAPI by lazy {
        retrofitClient
            .build()
            .create(WeatherAPI::class.java)
    }
}