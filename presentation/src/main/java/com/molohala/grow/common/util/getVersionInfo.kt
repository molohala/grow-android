package com.molohala.grow.common.util

import android.content.Context
import android.content.pm.PackageManager

fun getVersionInfo(context: Context): String? {
    var version: String? = null
    try {
        val i = context.packageManager.getPackageInfo(context.packageName, 0)
        version = i.versionName
    } catch (e: PackageManager.NameNotFoundException) {
    }
    return version
}