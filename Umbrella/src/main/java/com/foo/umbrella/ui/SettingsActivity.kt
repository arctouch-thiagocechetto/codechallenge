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

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SettingsActivity: AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    fragmentManager.beginTransaction()
        .replace(android.R.id.content, SettingsFragment())
        .commit()
  }
}
