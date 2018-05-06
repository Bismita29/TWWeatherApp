package tw.com.weatherapp.ui.weatherdetail

import tw.com.weatherapp.model.WeatherEntity

interface WeatherDetailFragmentScreen {
    abstract fun showLoadingSpinner()
    abstract fun dismissLoadingSpinner()
    abstract fun getLocation(lat: Double, lng: Double)
    abstract fun onWeatherDetailReceived(weatherEntity: WeatherEntity)
    abstract fun onError()
}