package com.foo.umbrella.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.foo.umbrella.R
import com.foo.umbrella.util.getColorCompat

class SettingsActivity: AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    supportActionBar?.setBackgroundDrawable(ColorDrawable(getColorCompat(R.color.settings_action_bar)))

    fragmentManager.beginTransaction()
        .replace(android.R.id.content, SettingsFragment())
        .commit()
  }
}
