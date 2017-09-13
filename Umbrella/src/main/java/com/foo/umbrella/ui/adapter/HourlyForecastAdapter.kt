package com.foo.umbrella.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.foo.umbrella.R
import com.foo.umbrella.data.model.ForecastCondition
import com.foo.umbrella.util.getColorCompat
import com.foo.umbrella.util.inflate
import kotlinx.android.synthetic.main.hourly_item.view.hour
import kotlinx.android.synthetic.main.hourly_item.view.icon
import kotlinx.android.synthetic.main.hourly_item.view.temperature

class HourlyForecastAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var minTemperature: ForecastCondition? = null
  private var maxTemperature: ForecastCondition? = null

  var forecast: List<ForecastCondition> = emptyList()
    set(value) {
      minTemperature = value.minBy { it.tempCelsius.toFloat() }
      maxTemperature = value.maxBy { it.tempCelsius.toFloat() }
      field = value
    }
    get() = field

  var showCelsius: Boolean = false
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ForecastHolder(parent.inflate(R.layout.hourly_item))

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = with(forecast.get(position)) {
    with(holder as ForecastHolder) {
      forecast.maxBy { it.tempCelsius.toFloat() }
      timeLabel.text = displayTime
      val tempUnit = if (showCelsius) tempCelsius else tempFahrenheit
      temperatureLabel.text = temperatureLabel.context.getString(R.string.temperature, tempUnit)
      iconImageView.setImageResource(getResourceId(icon))

      if (forecast.get(position).equals(minTemperature)) {
        setItemColor(holder, R.color.weather_cool)
      }

      if (forecast.get(position).equals(maxTemperature)) {
        setItemColor(holder, R.color.weather_warm)
      }
    }
  }

  private fun setItemColor(holder: RecyclerView.ViewHolder, colorResId: Int) = with(holder as ForecastHolder) {
    var color = iconImageView.context.getColorCompat(colorResId)
    iconImageView.setColorFilter(color)
    timeLabel.setTextColor(color)
    temperatureLabel.setTextColor(color)
  }


  fun getResourceId(icon: String) = when (icon) {
    "cloudy" -> R.drawable.weather_cloudy
    "fog" -> R.drawable.weather_fog
    "sleet" -> R.drawable.weather_hail
    "chancetstorms" -> R.drawable.weather_lightning
    "tstorms" -> R.drawable.weather_lightning_rainy
    "partlycloudy" -> R.drawable.weather_partlycloudy
    "rain" -> R.drawable.weather_rainy
    "chancerain" -> R.drawable.weather_rainy
    "snow" -> R.drawable.weather_snowy
    "chancesnow" -> R.drawable.weather_snowy
    "hazy" -> R.drawable.weather_snowy_rainy
    "clear" -> R.drawable.weather_sunny
    "sunny" -> R.drawable.weather_sunny
    else -> R.drawable.weather_cloudy
  }

  override fun getItemCount() = forecast.count()

  class ForecastHolder(view: View): RecyclerView.ViewHolder(view) {
    val timeLabel: TextView = view.hour
    val temperatureLabel: TextView = view.temperature
    val iconImageView: ImageView = view.icon
  }

}
