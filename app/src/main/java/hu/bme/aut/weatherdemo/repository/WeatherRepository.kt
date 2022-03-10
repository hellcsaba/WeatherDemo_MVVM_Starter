package hu.bme.aut.weatherdemo.repository

import hu.bme.aut.weatherdemo.datasource.WeatherNetworkDataSource
import hu.bme.aut.weatherdemo.util.NetworkResponse

class WeatherRepository {
    suspend fun getWeatherData(cityName: String): NetworkResponse<Any> {
        return WeatherNetworkDataSource.getWeatherData(cityName)
    }
}