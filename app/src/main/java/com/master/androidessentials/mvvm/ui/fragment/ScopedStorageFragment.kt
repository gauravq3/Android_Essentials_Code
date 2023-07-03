package com.master.androidessentials.mvvm.ui.fragment

import android.Manifest
import android.content.*
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.master.androidessentials.R
import com.master.androidessentials.databinding.FragmentScopedStorageBinding
import com.master.androidessentials.mvvm.ui.base.BaseFragment
import com.master.androidessentials.utils.PermissionManager
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
import javax.inject.Inject

@AndroidEntryPoint
class ScopedStorageFragment : BaseFragment<FragmentScopedStorageBinding>(),
    PermissionManager.PermissionCallback {

    private var newUri: Uri? = null
    lateinit var resolver: ContentResolver

    @Inject
    lateinit var permissionManager: PermissionManager

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentScopedStorageBinding.inflate(layoutInflater)

    fun onGetSavedData() {
        getFileContent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resolver = context?.contentResolver!!
        binding.fragment = this

    }


    fun storeData() {
        if (permissionManager.isPermissionGranted(Manifest.permission.CAMERA)) {
            if (binding.textInputEditText.text.toString().isNotEmpty()) {
                saveTextToFile(binding.textInputEditText.text.toString())
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.enter_some_text),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {
            requestPermissions()
        }

    }


    private fun requestPermissions() {
        permissionManager.requestPermissions(
            Manifest.permission.CAMERA,
            // Add more permissions here if needed
        )
    }

    //Saving in Downloads of app
    private fun saveTextToFile(dataToSave: String) {

        if (newUri == null) {
            val displayName = "my_text_file1.txt"
            val mimeType = getString(R.string.mime_plaintext)

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            }


            val contentUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

            newUri = resolver.insert(contentUri, contentValues)

        }
        if (newUri != null) {
            val outputStream = resolver?.openOutputStream(newUri!!)
            outputStream?.use { stream: OutputStream ->
                stream.write("".toByteArray())
                stream.write(dataToSave.toByteArray())
            }
            binding.textInputEditText.text?.clear()
        }

    }

    private fun getFileContent() {


        /*When you dont have uri, you can use below code*/

//        val displayName = "my_text_file1.txt"
//        val mimeType = "text/plain"
//
//        val contentUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
//        val projection = arrayOf(MediaStore.Files.FileColumns._ID)
//
//        val selection =
//            "${MediaStore.Files.FileColumns.DISPLAY_NAME} = ? AND ${MediaStore.Files.FileColumns.MIME_TYPE} = ?"
//        val selectionArgs = arrayOf(displayName, mimeType)
//
//        val cursor = contentResolver.query(contentUri, projection, selection, selectionArgs, null)
//
//
//        if (cursor != null && cursor.moveToFirst()) {
//            val fileIdColumnIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)
//            val fileId = cursor.getLong(fileIdColumnIndex)
//            newUri = ContentUris.withAppendedId(contentUri, fileId)
//        }
//
//        cursor?.close()


        if (newUri != null) {
            val inputStream = resolver.openInputStream(newUri!!)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }

            val fileContents = stringBuilder.toString()
            binding.dataFromFile.text = fileContents
            Log.e("printLog", fileContents)
        }


    }

    //saving in Documents folder
    fun saveDataInDocs() {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "my_file.txt")
            put(MediaStore.MediaColumns.MIME_TYPE, getString(R.string.mime_plaintext))
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
        }

        val uri = resolver.insert(
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
            contentValues
        )
        uri?.let { documentUri ->
            resolver.openOutputStream(documentUri).use { outputStream ->
                val text = "Hello, World!"
                outputStream?.write(text.toByteArray())
            }
        }

    }


    fun saveInPrivate() {
        val fileContent = "This is a file created by Gaurav."
        val fileName = "fileFirst.txt"
        val fileOutputStream = requireContext().openFileOutput(fileName, Context.MODE_PRIVATE)
        fileOutputStream.write(fileContent.toByteArray())
        fileOutputStream.close()


    }

    fun getPrivateFileContents() {
        val fileName = "fileFirst.txt"
        val file = File(requireContext().filesDir, fileName)

        if (file.exists()) {
            // File exists, you can read its contents
            val fileInputStream = FileInputStream(file)
            val fileContents = fileInputStream.bufferedReader().use { it.readText() }
            fileInputStream.close()

            // Do something with the file contents
            binding.filedataprivate.text = fileContents
            Log.d("FileContent", fileContents)
        } else {
            // File does not exist
            Log.d("FileStatus", "File not found")
        }
    }


    override fun onPermissionsGranted(grantedPermissions: List<String>) {
        // Handle granted permissions
        Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_SHORT).show()
        saveTextToFile("Hello World")
    }

    override fun onPermissionsDenied(deniedPermissions: List<String>) {
        Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        // Handle denied permissions
    }


}
