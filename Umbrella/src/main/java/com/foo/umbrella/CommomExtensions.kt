package com.foo.umbrella

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) = Toast.makeText(this, text, duration).show()

fun Activity.launchActivity(destinationClass: Class<*>, flags: Int = 0) {
  val intent = Intent(this, destinationClass)
  intent.flags = flags
  startActivity(intent)
}