package com.master.androidessentials.utils


import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager(private val activity: Activity) {

    fun checkPermission(permission: String): Boolean {
        val result = ContextCompat.checkSelfPermission(activity, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    }

    fun requestPermissions(permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
        callback: PermissionCallback
    ) {
        if (requestCode == callback.getRequestCode()) {
            val grantedPermissions = mutableListOf<String>()
            val deniedPermissions = mutableListOf<String>()

            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.add(permissions[i])
                } else {
                    deniedPermissions.add(permissions[i])
                }
            }

            if (deniedPermissions.isEmpty()) {
                callback.onPermissionsGranted(grantedPermissions)
            } else {
                callback.onPermissionsDenied(grantedPermissions, deniedPermissions)
            }
        }
    }

    interface PermissionCallback {
        fun onPermissionsGranted(grantedPermissions: List<String>)
        fun onPermissionsDenied(grantedPermissions: List<String>, deniedPermissions: List<String>)
        fun getRequestCode(): Int
    }
}
