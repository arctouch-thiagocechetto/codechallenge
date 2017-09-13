package com.foo.umbrella.preseter

import com.foo.umbrella.R
import com.foo.umbrella.data.ApiServicesProvider
import com.foo.umbrella.data.model.WeatherData
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainPresenter(private val apiServicesProvider: ApiServicesProvider) {

  companion object {
    private const val MAX_COOL_TEMPERATURE_FARENHEIT = 60
  }

  private var subscription: Subscription? = null

  private val weatherService
    get() = apiServicesProvider.weatherService

  fun fetchData(zipCode: String, onSuccess: (WeatherData) -> Unit, onError: (String) -> Unit) {
    subscription = weatherService.forecastForZipObservable(zipCode)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { result ->
              if (result.isError) {
                onError(result.error().localizedMessage)
              } else {
                onSuccess(result.response().body())
              }
            },
            {
              onError(it.localizedMessage)
            }
        )
  }

  fun getTemperatureColor(temperatureFarenheit: String) = when {
    temperatureFarenheit.toFloat() < MAX_COOL_TEMPERATURE_FARENHEIT -> R.color.weather_cool
    else -> R.color.weather_warm
  }

  fun destroy() = subscription?.unsubscribe()
}