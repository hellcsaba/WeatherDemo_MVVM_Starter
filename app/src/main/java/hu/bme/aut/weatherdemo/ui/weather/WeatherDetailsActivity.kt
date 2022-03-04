package hu.bme.aut.weatherdemo.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import hu.bme.aut.weatherdemo.BuildConfig
import hu.bme.aut.weatherdemo.R
import hu.bme.aut.weatherdemo.databinding.CityRowBinding
import hu.bme.aut.weatherdemo.model.network.WeatherResult
import hu.bme.aut.weatherdemo.network.WeatherAPI
import kotlinx.android.synthetic.main.activity_weather_details.*
import kotlinx.android.synthetic.main.city_row.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class WeatherDetailsActivity : AppCompatActivity() {

    private val weatherViewModel : WeatherViewModel by viewModels()

    //private lateinit var binding: Binding

    companion object {
        const val KEY_CITY = "KEY_CITY"
    }

    private lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)

        cityName = intent.getStringExtra(KEY_CITY)!!
        tvCity.text = cityName
    }

    override fun onResume() {
        super.onResume()

        getWeatherData()
    }

    private fun getWeatherData() {
        weatherViewModel.getWeatherData(cityName)?.observe(this,
            object: Observer<WeatherResult> {
                override fun onChanged(weatherResult: WeatherResult) {
                    val icon = weatherResult?.weather?.get(0)?.icon
                    processResponse(weatherResult, icon)
                    weatherViewModel.getWeatherData(cityName)?.removeObserver(this)
                }
            })
    }

    private fun processResponse(
        weatherData: WeatherResult?,
        icon: String?
    ) {
        Glide.with(this@WeatherDetailsActivity)
            .load("https://openweathermap.org/img/w/$icon.png")
            .into(ivWeatherIcon)


        tvMain.text = weatherData?.weather?.get(0)?.main
        tvDescription.text = weatherData?.weather?.get(0)?.description
        tvTemperature.text =
            getString(R.string.temperature, weatherData?.main?.temp?.toFloat().toString())

        val sdf = SimpleDateFormat("h:mm a z", Locale.getDefault())
        val sunriseDate = Date((weatherData?.sys?.sunrise?.toLong())!! * 1000)
        val sunriseTime = sdf.format(sunriseDate)
        tvSunrise.text = getString(R.string.sunrise, sunriseTime)
        val sunsetDate = Date(weatherData.sys.sunset?.toLong()!! * 1000)
        val sunsetTime = sdf.format(sunsetDate)
        tvSunset.text = getString(R.string.sunset, sunsetTime)
    }

}