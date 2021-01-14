package com.nanbox.xposedwifi

import de.robv.android.xposed.XSharedPreferences

/**
 * Created by nanbox on 2021/1/14.
 */
object XposedSPUtil {

    private const val SP_NAME = "WIFICHANGE"
    private const val SSID = "SSID"
    private const val BSSID = "BSSID"

    private val sp by lazy {
        XSharedPreferences("com.nanbox.xposedwifi", SP_NAME)
    }

    fun getSSID(): String? {
        return sp.getString(SSID, null)
    }

    fun getBSSID(): String? {
        return sp.getString(BSSID, null)
    }
}