package hu.bme.aut.weatherdemo.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.weatherdemo.model.network.WeatherResult
import hu.bme.aut.weatherdemo.repository.WeatherRepository
import hu.bme.aut.weatherdemo.util.NetworkErrorResult
import hu.bme.aut.weatherdemo.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel(){
    private var weatherRepository: WeatherRepository = WeatherRepository()

    private val result = MutableLiveData<WeatherViewState>()

    fun getWeatherLiveData() = result

    fun getWeatherData(cityName: String){
        viewModelScope.launch(Dispatchers.IO){
            val response = weatherRepository.getWeatherData(cityName)
            when (response) {
                is NetworkResult -> {
                    val weatherResult = response.result as WeatherResult
                    result.postValue(WeatherResponseSucces(weatherResult))
                }
                is NetworkErrorResult -> {
                    result.postValue(WeatherResponseError(response.errorMsg.message!!))
                }
            }
        }
    }
}