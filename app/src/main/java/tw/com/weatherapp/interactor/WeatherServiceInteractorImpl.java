package tw.com.weatherapp.interactor;

import tw.com.weatherapp.network.IRestRequestListener;
import tw.com.weatherapp.service.WeatherServiceHelper;

public class WeatherServiceInteractorImpl implements IWeatherServiceInteractor {
    @Override
    public void fetchWeatherData(String city, String country, IRestRequestListener restRequestListener) {
        WeatherServiceHelper.fetchWeather(city, country, restRequestListener);
    }
}
