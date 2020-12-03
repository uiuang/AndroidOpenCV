package com.uiuang.learning

import android.app.Application

class App : Application() {

    companion object{
        const val TAG = "OpenCv"
        lateinit var app: Application
    }
    override fun onCreate() {
        super.onCreate()

    }
}