package com.foo.umbrella.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.foo.umbrella.R
import com.foo.umbrella.data.ApiServicesProvider
import com.foo.umbrella.data.model.DailyForecast
import com.foo.umbrella.data.model.ForecastCondition
import com.foo.umbrella.data.model.WeatherData
import com.foo.umbrella.launchActivity
import com.foo.umbrella.preseter.MainPresenter
import com.foo.umbrella.showToast
import kotlinx.android.synthetic.main.activity_main.city
import kotlinx.android.synthetic.main.activity_main.daysList
import kotlinx.android.synthetic.main.activity_main.description
import kotlinx.android.synthetic.main.activity_main.header
import kotlinx.android.synthetic.main.activity_main.settings
import kotlinx.android.synthetic.main.activity_main.temperature
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.TextStyle
import java.util.Locale

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

    daysList.layoutManager = LinearLayoutManager(this)
    daysList.adapter = DailyForecastAdapter()
  }

  private fun updateUi(weatherData: WeatherData, unit: String) {
    val isCelsius = unit.equals(getString(R.string.unit_celsius))
    with(weatherData.currentObservation) {
      val tempUnit = if (isCelsius) tempCelsius else tempFahrenheit
      temperature.text = getString(R.string.temperature, tempUnit)
      city.text = displayLocation.fullName
      description.text = weatherDescription
      header.setBackgroundColor(ContextCompat.getColor(this@MainActivity, presenter.getTemperatureColor(tempFahrenheit)))
    }

    with(daysList.adapter as DailyForecastAdapter) {
      forecasts = weatherData.forecast
          .groupBy { it.dateTime.dayOfYear }
          .map { DailyForecast(getItemTitle(it.value.first()), it.value) }
      showCelsius = isCelsius
      notifyDataSetChanged()
    }
  }

  private fun getItemTitle(forecastCondition: ForecastCondition): String {
    val today = LocalDateTime.now().dayOfYear
    return when (forecastCondition.dateTime.dayOfYear) {
      today -> getString(R.string.today)
      today + 1 -> getString(R.string.tomorrow)
      else -> forecastCondition.dateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }
  }

  private fun showMessage(message: String) = showToast(message)

  override fun onDestroy() {
    presenter.destroy()
    super.onDestroy()
  }
}