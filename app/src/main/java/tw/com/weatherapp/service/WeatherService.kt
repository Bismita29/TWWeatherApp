package tw.com.weatherapp.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tw.com.weatherapp.model.Location
import tw.com.weatherapp.model.WeatherEntity

private const val APP_ID = "8ff52f7cf4c2389cace4e601ba042f57"
interface WeatherService {

    @GET("/data/2.5/weather?")
    fun fetchWeatherData(@Query("location") location: String,@Query("appid") appID: String = APP_ID): Call<WeatherEntity>

}