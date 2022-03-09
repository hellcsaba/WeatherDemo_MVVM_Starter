package hu.bme.aut.weatherdemo.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.weatherdemo.BuildConfig
import hu.bme.aut.weatherdemo.model.network.WeatherResult
import hu.bme.aut.weatherdemo.network.RetrofitClient
import hu.bme.aut.weatherdemo.ui.weather.WeatherResponseError
import hu.bme.aut.weatherdemo.ui.weather.WeatherResponseSucces
import hu.bme.aut.weatherdemo.ui.weather.WeatherViewState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WeatherNetworkDataSource {

    fun getWeatherData(cityName: String): MutableLiveData<WeatherViewState>{
        val call = RetrofitClient.apiInterface.getWeatherData(cityName, "metric", BuildConfig.WEATHER_API_KEY)

        val weatherResultData = MutableLiveData<WeatherViewState>()

        call.enqueue(object : Callback<WeatherResult> {
            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                Log.d("DEBUG : ", t.message.toString())
                weatherResultData.value = WeatherResponseError(t.message!!)
            }

            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                weatherResultData.value = WeatherResponseSucces(response.body()!!)
                Log.d("DEBUG : ", response.body().toString())

            }
        })

        return weatherResultData
    }
}