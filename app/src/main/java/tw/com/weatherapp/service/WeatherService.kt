package tw.com.weatherapp.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import tw.com.weatherapp.model.Location
import tw.com.weatherapp.model.WeatherEntity

interface WeatherService {

    @GET("/data/2.5/weather")
    abstract fun fetchWeatherData(@QueryMap data: Map<String, String>): Call<WeatherEntity>
}