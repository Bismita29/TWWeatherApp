package tw.com.weatherapp.interactor;

import tw.com.weatherapp.network.IRestRequestListener;
/*
Wrapper over model
 */
public interface IWeatherServiceInteractor {
    void fetchWeatherData(String city, String country, IRestRequestListener restRequestListener);
}
