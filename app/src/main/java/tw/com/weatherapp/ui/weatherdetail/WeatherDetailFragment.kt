package tw.com.weatherapp.ui.weatherdetail

import android.content.Context
import android.widget.TextView
import android.support.v4.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import tw.com.weatherapp.IFragmentInteractionListener
import tw.com.weatherapp.LocationUtil
import tw.com.weatherapp.R
import tw.com.weatherapp.interactor.WeatherServiceInteractorImpl
import tw.com.weatherapp.model.WeatherEntity

/*
View class which has the instance of presenter
 */
class WeatherDetailFragment : Fragment(),WeatherDetailFragmentScreen {

    private lateinit var city: TextView
    private lateinit var details: TextView
    private lateinit var temp: TextView
    private lateinit var weatherIcon: TextView

    lateinit var handler: Handler

    private lateinit var mListener: IFragmentInteractionListener
    private lateinit var presenter: WeatherDetailFragmentPresenter


    companion object {

        fun newInstance(param1: String, param2: String): WeatherDetailFragment {
        return WeatherDetailFragment()
    }
    }

//    companion object {
//        fun newInstance(): WeatherDetailFragment {
//            return WeatherDetailFragment()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = WeatherDetailFragmentPresenter(WeatherServiceInteractorImpl(), this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.weather_detail_frag, container, false)
        city = rootView.findViewById(R.id.city)
        details = rootView.findViewById(R.id.details_field)
        temp = rootView.findViewById(R.id.temp)
        weatherIcon = rootView.findViewById(R.id.icon)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lat = 12.9716
        val lng = 77.5946
        presenter.getLocation(java.lang.Double.valueOf(lat), java.lang.Double.valueOf(lng))
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IFragmentInteractionListener) {
            mListener = context as IFragmentInteractionListener
        } else {
            throw RuntimeException(context!!.toString() + " must implement IFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun showLoadingSpinner() {
        mListener.showProgressDialog(getString(R.string.loading))
    }

    override fun dismissLoadingSpinner() {
        mListener.dismissProgressDialog()
    }

    override fun getLocation(lat: Double, lng: Double) {
        Handler().post {
            val location = LocationUtil.getLocation(activity, lat, lng)
            presenter.getWeatherForLocation(location.getCity(), location.getCountry())
        }
    }

    override fun onWeatherDetailReceived(weatherEntity: WeatherEntity) {
            city.setText(weatherEntity.name +","+weatherEntity.sys.country);
            //details.setText(weatherEntity.weather.get
            temp.setText(java.lang.Double.toString(weatherEntity.main.temp))
            //weatherIcon.setText(getActivity().getString(R.string.weather_sunny));


    }

    override fun onError() {
        //handle it
    }

}