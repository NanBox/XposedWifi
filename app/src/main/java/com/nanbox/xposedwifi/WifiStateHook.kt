package com.nanbox.xposedwifi

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class WifiStateHook : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        try {
            if (!XposedSPUtil.getIsWifi()) {
                return
            }

            XposedHelpers.findAndHookMethod(
                    WifiManager::class.java, "isWifiEnabled",
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam?) {
                            Log.w("info", "start hook isWifiEnabled")
                            param?.result = true
                        }
                    })

            XposedHelpers.findAndHookMethod(
                    ConnectivityManager::class.java, "getActiveNetworkInfo",
                    object : XC_MethodHook() {

                        override fun afterHookedMethod(param: MethodHookParam?) {
                            super.afterHookedMethod(param)
                            Log.w("info", "start hook getActiveNetworkInfo")
                            val networkInfoClass = XposedHelpers.findClass(NetworkInfo::class.java.name, lpparam?.classLoader)
                            val constructor = networkInfoClass.getConstructor(
                                    Int::class.java,
                                    Int::class.java,
                                    String::class.java,
                                    String::class.java
                            )

                            val networkInfo = constructor.newInstance(
                                    ConnectivityManager.TYPE_WIFI,
                                    0,
                                    "WIFI",
                                    ""
                            )
                            XposedHelpers.setBooleanField(networkInfo, "mIsAvailable", true)
                            XposedHelpers.setObjectField(networkInfo, "mState", NetworkInfo.State.CONNECTED)
                            XposedHelpers.setObjectField(networkInfo, "mDetailedState", NetworkInfo.DetailedState.CONNECTED)


                            Log.w("info", "hook getActiveNetworkInfo source:${param?.result} result:$networkInfo")
                            param?.result = networkInfo
                        }
                    })

            XposedHelpers.findAndHookMethod(
                    ConnectivityManager::class.java, "getNetworkInfo", Int::class.java,
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam?) {
                            Log.w("info", "start hook getNetworkInfo")
                            if (param?.args?.get(0) == ConnectivityManager.TYPE_WIFI) {
                                var result = param.result as NetworkInfo?
                                if (result == null) {
                                    val networkInfoClass = XposedHelpers.findClass(NetworkInfo::class.java.name, lpparam?.classLoader)
                                    val constructor = networkInfoClass.getConstructor(
                                            Int::class.java,
                                            Int::class.java,
                                            String::class.java,
                                            String::class.java
                                    )

                                    result = constructor.newInstance(
                                            ConnectivityManager.TYPE_WIFI,
                                            0,
                                            "WIFI",
                                            ""
                                    ) as NetworkInfo?
                                    param.result = result
                                }
                                XposedHelpers.setBooleanField(result, "mIsAvailable", true)
                                XposedHelpers.setObjectField(result, "mState", NetworkInfo.State.CONNECTED)
                                XposedHelpers.setObjectField(result, "mDetailedState", NetworkInfo.DetailedState.CONNECTED)
                            }
                        }
                    }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}