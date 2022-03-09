package hu.bme.aut.weatherdemo.ui.weather

import hu.bme.aut.weatherdemo.model.network.WeatherResult

sealed class WeatherViewState

data class WeatherResponseSucces(val data: WeatherResult) :WeatherViewState()
data class WeatherResponseError(val exceptionMsg: String) :WeatherViewState()