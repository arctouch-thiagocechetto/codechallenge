package com.foo.umbrella.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.foo.umbrella.R
import com.foo.umbrella.data.ApiServicesProvider
import com.foo.umbrella.data.model.WeatherData
import com.foo.umbrella.preseter.MainPresenter
import com.foo.umbrella.showToast

class MainActivity: AppCompatActivity() {

  private val presenter by lazy { MainPresenter(ApiServicesProvider(application)) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onStart() {
    super.onStart()
    presenter.fetchData(this::updateUi, this::showMessage)
  }

  private fun updateUi(weatherData: WeatherData) {

  }

  private fun showMessage(message: String) = showToast(message)

  override fun onDestroy() {
    presenter.destroy()
    super.onDestroy()
  }
}
