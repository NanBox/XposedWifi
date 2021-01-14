package com.nanbox.xposedwifi

import android.content.Context

/**
 * Created by linnanquan on 2021/1/14.
 */
object SPUtil {

    private const val SP_NAME = "WIFICHANGE"
    private const val SSID = "SSID"
    private const val BSSID = "BSSID"

    private val sp by lazy {
        AppShell.context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    fun setSSID(ssid: String) {
        sp.edit().putString(SSID, ssid).apply()
    }

    fun setBSSID(bssid: String) {
        sp.edit().putString(BSSID, bssid).apply()
    }

    fun getSSID(): String? {
        return sp.getString(SSID, null)
    }

    fun getBSSID(): String? {
        return sp.getString(BSSID, null)
    }
}