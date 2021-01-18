package com.nanbox.xposedwifi

import android.content.Context

/**
 * Created by nanbox on 2021/1/14.
 */
object SPUtil {

    private const val SP_NAME = "WIFICHANGE"
    private const val SSID = "SSID"
    private const val BSSID = "BSSID"
    private const val WIFI = "WIFI"

    private val sp by lazy {
        AppShell.context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    fun setSSID(ssid: String) {
        sp.edit().putString(SSID, ssid).apply()
    }

    fun getSSID(): String? {
        return sp.getString(SSID, null)
    }

    fun setBSSID(bssid: String) {
        sp.edit().putString(BSSID, bssid).apply()
    }

    fun getBSSID(): String? {
        return sp.getString(BSSID, null)
    }

    fun setIsWifi(isWifi: Boolean) {
        sp.edit().putBoolean(WIFI, isWifi).apply()
    }

    fun getIsWifi(): Boolean {
        return sp.getBoolean(WIFI, false)
    }
}