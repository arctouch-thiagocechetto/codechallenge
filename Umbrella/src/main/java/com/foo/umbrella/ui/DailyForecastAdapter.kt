/*
 * Copyright 2017 ArcTouch LLC.
 * All rights reserved.
 *
 * This file, its contents, concepts, methods, behavior, and operation
 * (collectively the "Software") are protected by trade secret, patent,
 * and copyright laws. The use of the Software is governed by a license
 * agreement. Disclosure of the Software to third parties, in any form,
 * in whole or in part, is expressly prohibited except as authorized by
 * the license agreement.
 */

package com.foo.umbrella.ui

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.foo.umbrella.R
import com.foo.umbrella.data.model.DailyForecast
import com.foo.umbrella.inflate
import kotlinx.android.synthetic.main.day_forecast.view.hours
import kotlinx.android.synthetic.main.day_forecast.view.title

class DailyForecastAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  companion object {
    private const val NUMBER_OF_COLUMNS = 4
  }

  var showCelsius: Boolean = false
  var forecasts: List<DailyForecast> = emptyList()

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) = with(holder as DailyForecastHolder) {
    with(forecasts.get(position)) {
      titleTextView.text = title
      val adapter = HourlyForecastAdapter()
      adapter.forecast = forecast
      adapter.showCelsius = showCelsius
      hourlyForecastListView.layoutManager = GridLayoutManager(hourlyForecastListView.context, NUMBER_OF_COLUMNS)
      hourlyForecastListView.adapter = adapter
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DailyForecastHolder(parent.inflate(R.layout.day_forecast))

  override fun getItemCount() = forecasts.count()

  class DailyForecastHolder(view: View): RecyclerView.ViewHolder(view) {
    val titleTextView: TextView = view.title
    val hourlyForecastListView: RecyclerView = view.hours
  }
}
