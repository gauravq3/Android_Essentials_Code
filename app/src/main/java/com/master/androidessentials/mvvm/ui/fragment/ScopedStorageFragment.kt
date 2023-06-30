package com.master.androidessentials.mvvm.ui.fragment

import android.Manifest
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.master.androidessentials.databinding.FragmentScopedStorageBinding
import com.master.androidessentials.mvvm.ui.base.BaseFragment
import com.master.androidessentials.utils.PermissionManager
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class ScopedStorageFragment : BaseFragment<FragmentScopedStorageBinding>(),
    PermissionManager.PermissionCallback {

    @Inject
    lateinit var permissionManager: PermissionManager
    private val deniedPermissions = mutableListOf<String>()
    private val SINGLE_PERMISSION_REQUEST_CODE = 1001
    private val MULTIPLE_PERMISSIONS_REQUEST_CODE = 1002
    private val singlePermissionToRequest = Manifest.permission.CAMERA
    private val multiplePermissionsToRequest = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted
                onPermissionGranted()
            } else {
                // Permission denied
                onPermissionDenied()
            }
        }


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentScopedStorageBinding.inflate(layoutInflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        askForPermissions()

        binding.saveToFile.setOnClickListener {

        }

    }

    private fun askForPermissions() {
        // Request single permission if needed
        if (!permissionManager.checkPermission(singlePermissionToRequest)) {
            requestPermissionLauncher.launch(singlePermissionToRequest)
        }

        // Request multiple permissions if needed
        val permissionsToAsk = multiplePermissionsToRequest.filterNot { permission ->
            permissionManager.checkPermission(permission)
        }

        for (permission in multiplePermissionsToRequest) {
            if (!permissionManager.checkPermission(permission)) {
                deniedPermissions.add(permission)
            }
        }
        if (deniedPermissions.isNotEmpty()) {
            requestPermissionLauncher.launch(deniedPermissions.toTypedArray().toString())
        }
    }

    private fun saveTextToFile(text: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "testingFile.txt")
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
        }
        val resolver = activity?.contentResolver
        var uri: Uri? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_DOCUMENTS
            )

            val collection = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            uri = resolver?.insert(collection, contentValues)
        } else {
            val directory =
                File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOCUMENTS)
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val file = File(directory, "my_file.txt")
            uri = Uri.fromFile(file)
        }

        uri?.let {
            try {
                val outputStream = resolver?.openOutputStream(uri)
                outputStream?.bufferedWriter().use { writer ->
                    writer?.write(text)
                }
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    override fun onPermissionsGranted(grantedPermissions: List<String>) {
        onPermissionsGranted(grantedPermissions)
    }

    override fun onPermissionsDenied(
        grantedPermissions: List<String>,
        deniedPermissions: List<String>
    ) {
        // Check if any permissions are permanently denied
        val permanentlyDeniedPermissions = deniedPermissions.filter { permission ->
            !shouldShowRequestPermissionRationale(permission)
        }

        if (permanentlyDeniedPermissions.isNotEmpty()) {
            // Some permissions are permanently denied
            showPermissionDeniedDialog()
        } else {
            // Permissions are denied but not permanently
            onPermissionDenied()
        }
    }

    private fun showPermissionDeniedDialog() {
        // Display a dialog explaining that the permissions are required and provide instructions to open app settings
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Permission Required")
            .setMessage("Please grant the necessary permissions from the app settings.")
            .setPositiveButton("Open Settings") { _, _ ->
                openAppSettings()
            }
            .setNegativeButton("Cancel") { _, _ ->
                // Handle cancellation if needed
            }
            .show()
    }

    private fun openAppSettings() {
        // Open the app settings screen where the user can manually grant permissions
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", requireContext().packageName, null)
        startActivity(intent)
    }

    override fun getRequestCode(): Int {
        return 102
    }

    private fun onPermissionGranted() {
        // Perform operation that requires the permission
    }

    private fun onPermissionDenied() {
        // Handle denied permissions
    }


}

data class MyFile(val contentUri: Uri, val displayName: String, val mimeType: String)
