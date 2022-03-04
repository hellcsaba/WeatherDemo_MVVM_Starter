package hu.bme.aut.weatherdemo.repository

import androidx.lifecycle.MutableLiveData
import hu.bme.aut.weatherdemo.datasource.WeatherNetworkDataSource
import hu.bme.aut.weatherdemo.model.network.WeatherResult

class WeatherRepository {
    fun getWeatherData(cityName: String): MutableLiveData<WeatherResult>{
        return WeatherNetworkDataSource.getWeatherData(cityName)
    }

}