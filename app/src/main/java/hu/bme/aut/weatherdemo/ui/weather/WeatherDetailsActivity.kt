package hu.bme.aut.weatherdemo.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import hu.bme.aut.weatherdemo.R
import hu.bme.aut.weatherdemo.databinding.ActivityWeatherDetailsBinding
import hu.bme.aut.weatherdemo.model.network.WeatherResult
import kotlinx.android.synthetic.main.activity_weather_details.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherDetailsActivity : AppCompatActivity() {

    private val weatherViewModel : WeatherViewModel by viewModels()

    private lateinit var binding: ActivityWeatherDetailsBinding

    companion object {
        const val KEY_CITY = "KEY_CITY"
    }

    private lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cityName = intent.getStringExtra(KEY_CITY)!!
        binding.tvCity.text = cityName
    }

    override fun onResume() {
        super.onResume()

        getWeatherData()
    }

    private fun getWeatherData() {
        weatherViewModel.getWeatherData(cityName)?.observe(this,
            {weatherViewState -> render(weatherViewState)})
    }

    private fun processResponse(
        weatherData: WeatherResult?,
        icon: String?
    ) {
        Glide.with(this@WeatherDetailsActivity)
            .load("https://openweathermap.org/img/w/$icon.png")
            .into(ivWeatherIcon)


        binding.tvMain.text = weatherData?.weather?.get(0)?.main
        binding.tvDescription.text = weatherData?.weather?.get(0)?.description
        binding.tvTemperature.text =
            getString(R.string.temperature, weatherData?.main?.temp?.toFloat().toString())

        val sdf = SimpleDateFormat("h:mm a z", Locale.getDefault())
        val sunriseDate = Date((weatherData?.sys?.sunrise?.toLong())!! * 1000)
        val sunriseTime = sdf.format(sunriseDate)
        binding.tvSunrise.text = getString(R.string.sunrise, sunriseTime)
        val sunsetDate = Date(weatherData.sys.sunset?.toLong()!! * 1000)
        val sunsetTime = sdf.format(sunsetDate)
        binding.tvSunset.text = getString(R.string.sunset, sunsetTime)
    }

    private fun render(result: WeatherViewState){
        when(result){
            is WeatherResponseSucces -> {
                val icon = result.data.weather?.get(0)?.icon
                processResponse(result.data, icon)
            }
            is WeatherResponseError -> {
                binding.tvMain.text =result.exceptionMsg
            }
        }
    }

}