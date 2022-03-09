package hu.bme.aut.weatherdemo.repository

import androidx.lifecycle.MutableLiveData
import hu.bme.aut.weatherdemo.datasource.WeatherNetworkDataSource
import hu.bme.aut.weatherdemo.ui.weather.WeatherViewState

class WeatherRepository {
    fun getWeatherData(cityName: String): MutableLiveData<WeatherViewState>{
        return WeatherNetworkDataSource.getWeatherData(cityName)
    }
}