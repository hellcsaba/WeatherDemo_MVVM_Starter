package hu.bme.aut.weatherdemo.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.weatherdemo.BuildConfig
import hu.bme.aut.weatherdemo.model.network.WeatherResult
import hu.bme.aut.weatherdemo.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WeatherNetworkDataSource {

    fun getWeatherData(cityName: String): MutableLiveData<WeatherResult>{
        val call = RetrofitClient.apiInterface.getWeatherData(cityName, "metric", BuildConfig.WEATHER_API_KEY)

        val weatherResultData = MutableLiveData<WeatherResult>()

        call.enqueue(object : Callback<WeatherResult> {
            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                Log.d("DEBUG : ", t.message.toString())
            }

            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                weatherResultData.value = response.body()
                Log.d("DEBUG : ", response.body().toString())

            }
        })

        return weatherResultData
    }
}