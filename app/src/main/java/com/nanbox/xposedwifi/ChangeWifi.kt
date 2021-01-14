package com.nanbox.xposedwifi

import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage


/**
 * Created by nanbox on 2021/1/14.
 */
class ChangeWifi : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        try {
            val wifiInfoClass =
                XposedHelpers.findClassIfExists(
                    "android.net.wifi.WifiInfo",
                    lpparam.classLoader
                ) ?: return

            val ssid = XposedSPUtil.getSSID()
            if (!ssid.isNullOrBlank()) {
                XposedHelpers.findAndHookMethod(wifiInfoClass, "getSSID", object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun afterHookedMethod(param: MethodHookParam) {
                        Log.d("info", "android.net.wifi.WifiInfo---getSSID---$ssid")
                        param.result = ssid
                    }
                })
            }

            val bssid = XposedSPUtil.getBSSID()
            if (!ssid.isNullOrBlank()) {
                XposedHelpers.findAndHookMethod(
                    wifiInfoClass,
                    "getBSSID",
                    object : XC_MethodHook() {
                        @Throws(Throwable::class)
                        override fun afterHookedMethod(param: MethodHookParam) {
                            Log.d("info", "android.net.wifi.WifiInfo---getBSSID---$bssid")
                            param.result = bssid
                        }
                    })
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}