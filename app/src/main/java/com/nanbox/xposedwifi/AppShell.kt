package com.nanbox.xposedwifi

import android.app.Application
import android.content.Context

/**
 * Created by nanbox on 2021/1/14.
 */
class AppShell : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}