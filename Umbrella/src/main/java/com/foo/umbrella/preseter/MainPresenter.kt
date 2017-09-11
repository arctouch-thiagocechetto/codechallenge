package com.foo.umbrella.preseter

import com.foo.umbrella.data.ApiServicesProvider
import com.foo.umbrella.data.model.WeatherData
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainPresenter(private val apiServicesProvider: ApiServicesProvider) {

  private var subscription: Subscription? = null

  private val weatherService
    get() = apiServicesProvider.weatherService

  fun fetchData(onSuccess: (WeatherData) -> Unit, onError: (String) -> Unit) {
    subscription = weatherService.forecastForZipObservable("94107")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ onSuccess(it.response().body()) }, { onError(it.localizedMessage) })
  }

  fun destroy() = subscription?.unsubscribe()
}