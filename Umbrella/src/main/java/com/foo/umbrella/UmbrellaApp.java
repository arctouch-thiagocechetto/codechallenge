package com.foo.umbrella;

import com.jakewharton.threetenabp.AndroidThreeTen;

import android.app.Application;

public class UmbrellaApp extends Application {
  @Override public void onCreate() {
    super.onCreate();
    AndroidThreeTen.init(this);
  }
}
