package com.example.marvelchallenge

import android.app.Application
import com.example.marvelchallenge.di.AppComponent
import com.example.marvelchallenge.di.DaggerAppComponent
import com.example.marvelchallenge.di.AppModule
import timber.log.Timber

class MainApplication: Application() {

    val appComponent: AppComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}