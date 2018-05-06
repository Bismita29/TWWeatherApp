package tw.com.weatherapp

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import tw.com.weatherapp.ui.weatherdetail.WeatherDetailFragment

class MainActivity() : AppCompatActivity(), IFragmentInteractionListener {

    lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialog= ProgressDialog(this  )
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, WeatherDetailFragment.newInstance("","") ,"WeatherDetailFragment")
                   .commit()
        }

    }

    override fun showProgressDialog(message: String) {
        if (progressDialog != null)
            dismissProgressDialog()

        progressDialog.setMessage(message)
        progressDialog.show()
    }

    override fun dismissProgressDialog() {
        progressDialog.cancel()
        progressDialog.dismiss()

    }


}

