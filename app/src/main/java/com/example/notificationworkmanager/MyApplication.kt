package com.example.notificationworkmanager

import android.app.Application
import org.jetbrains.annotations.NotNull
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(
                    priority: Int,
                    tag: String?,
                    @NotNull message: String,
                    t: Throwable?
                ) {
                    super.log(priority, String.format("khurramTag", tag), message, t)
                }
            })
        }
    }
}