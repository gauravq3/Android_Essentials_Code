package com.master.androidessentials.utils


import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

class PermissionManager(private val fragment: Fragment) {
    private val requestPermissionLauncher =
        fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val grantedPermissions = mutableListOf<String>()
            val deniedPermissions = mutableListOf<String>()

            permissions.forEach { (permission, isGranted) ->
                if (isGranted) {
                    grantedPermissions.add(permission)
                } else {
                    deniedPermissions.add(permission)
                }
            }

            // Callback to handle the granted and denied permissions
            onPermissionsResult(grantedPermissions, deniedPermissions)
        }

    fun requestPermissions(vararg permissions: String) {
        val ungrantedPermissions = permissions.filterNot { isPermissionGranted(it) }
        if (ungrantedPermissions.isNotEmpty()) {
            requestPermissionLauncher.launch(ungrantedPermissions.toTypedArray())
        }
    }

     fun isPermissionGranted(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            fragment.requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Callback interface for handling the granted and denied permissions
    interface PermissionCallback {
        fun onPermissionsGranted(grantedPermissions: List<String>)
        fun onPermissionsDenied(deniedPermissions: List<String>)
    }

    // Example implementation of the PermissionCallback interface
    private fun onPermissionsResult(grantedPermissions: List<String>, deniedPermissions: List<String>) {
        if (fragment is PermissionCallback) {
            if (grantedPermissions.isNotEmpty()) {
                fragment.onPermissionsGranted(grantedPermissions)
            }
            if (deniedPermissions.isNotEmpty()) {
                fragment.onPermissionsDenied(deniedPermissions)
            }
        }
    }
}


