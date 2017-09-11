package com.foo.umbrella

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) = Toast.makeText(this, text, duration).show()