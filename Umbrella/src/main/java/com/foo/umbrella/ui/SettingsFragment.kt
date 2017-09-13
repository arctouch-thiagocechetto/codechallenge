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

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.ListPreference
import android.preference.PreferenceFragment
import android.preference.PreferenceGroup
import com.foo.umbrella.R

class SettingsFragment: PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.preferences)
    preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
  }

  override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
    updatePreference(key)
  }

  override fun onStart() {
    super.onStart()
    setUpPreferences(preferenceScreen)
  }

  private fun setUpPreferences(preferenceGroup: PreferenceGroup) {
    for (i in 0 until preferenceGroup.preferenceCount) {
      val preference = preferenceGroup.getPreference(i)
      if (preference is PreferenceGroup) {
        setUpPreferences(preference)
      } else {
        updatePreference(preference.key)
      }
    }
  }

  private fun updatePreference(key: String) {
    val preference = findPreference(key)
    if (preference is ListPreference) {
      preference.setSummary(preference.entry)
    }

    if (preference is EditTextPreference) {
      preference.setSummary(preference.text)
    }
  }
}
