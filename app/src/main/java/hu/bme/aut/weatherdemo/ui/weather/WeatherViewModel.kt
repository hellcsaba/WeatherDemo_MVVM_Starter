package hu.bme.aut.weatherdemo.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.weatherdemo.model.network.WeatherResult
import hu.bme.aut.weatherdemo.repository.WeatherRepository

class WeatherViewModel : ViewModel(){
    private var weatherRepository: WeatherRepository = WeatherRepository()

    fun getWeatherData(cityName: String): LiveData<WeatherResult>? {
        return weatherRepository.getWeatherData(cityName)
    }
}