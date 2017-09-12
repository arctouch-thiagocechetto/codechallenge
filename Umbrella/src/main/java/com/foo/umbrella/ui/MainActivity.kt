package com.foo.umbrella.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.foo.umbrella.R
import com.foo.umbrella.data.ApiServicesProvider
import com.foo.umbrella.data.model.WeatherData
import com.foo.umbrella.launchActivity
import com.foo.umbrella.preseter.MainPresenter
import com.foo.umbrella.showToast
import kotlinx.android.synthetic.main.activity_main.city
import kotlinx.android.synthetic.main.activity_main.header
import kotlinx.android.synthetic.main.activity_main.settings
import kotlinx.android.synthetic.main.activity_main.temperature

class MainActivity: AppCompatActivity() {

  private val presenter by lazy { MainPresenter(ApiServicesProvider(application)) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onStart() {
    super.onStart()
    settings.setOnClickListener { launchActivity(SettingsActivity::class.java) }

    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    val zip = preferences.getString(getString(R.string.settings_zip_key), getString(R.string.default_zipcode))
    val unit = preferences.getString(getString(R.string.settings_units_key), getString(R.string.unit_celsius))
    presenter.fetchData(zip, { updateUi(it, unit) }, this::showMessage)
  }

  private fun updateUi(weatherData: WeatherData, unit: String) {
    with(weatherData.currentObservation) {
      val tempUnit = if (unit.equals(getString(R.string.unit_celsius))) tempCelsius else tempFahrenheit
      temperature.text = getString(R.string.temperature, tempUnit)
      city.text = displayLocation.fullName
      header.setBackgroundColor(ContextCompat.getColor(this@MainActivity, presenter.getTemperatureColor(tempFahrenheit)))
    }
  }

  private fun showMessage(message: String) = showToast(message)

  override fun onDestroy() {
    presenter.destroy()
    super.onDestroy()
  }
}
