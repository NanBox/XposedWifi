package com.nanbox.xposedwifi

import android.net.wifi.WifiInfo
import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage


/**
 * Created by nanbox on 2021/1/14.
 */
class WifiInfoHook : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        try {
            val ssid = XposedSPUtil.getSSID()
            if (!ssid.isNullOrBlank()) {
                XposedHelpers.findAndHookMethod(
                        WifiInfo::class.java, "getSSID",
                        object : XC_MethodHook() {
                            override fun afterHookedMethod(param: MethodHookParam) {
                                Log.d("info", "android.net.wifi.WifiInfo---getSSID---$ssid")
                                param.result = ssid
                            }
                        })
            }

            val bssid = XposedSPUtil.getBSSID()
            if (!bssid.isNullOrBlank()) {
                XposedHelpers.findAndHookMethod(
                        WifiInfo::class.java, "getBSSID",
                        object : XC_MethodHook() {
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