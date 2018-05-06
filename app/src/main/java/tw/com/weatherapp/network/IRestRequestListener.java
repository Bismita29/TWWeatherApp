package tw.com.weatherapp.network;

public interface IRestRequestListener<T> {

    void onSuccess(T t);
    void onFailure(Throwable t);
}
