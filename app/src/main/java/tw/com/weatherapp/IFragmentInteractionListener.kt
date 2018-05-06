package tw.com.weatherapp

interface IFragmentInteractionListener {
    abstract fun showProgressDialog(message: String)
    abstract fun dismissProgressDialog()
}