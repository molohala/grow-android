package com.molohala.grow.application

import android.app.Application
import android.content.Context

class InfinityApp : Application() {

    companion object {
        lateinit var prefs: PreferenceManager

        private lateinit var instance: InfinityApp

        fun getContext(): Context {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}