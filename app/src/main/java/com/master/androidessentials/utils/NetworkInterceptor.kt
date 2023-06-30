package com.master.androidessentials.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.master.androidessentials.di.qualifiers.ApplicationContextQualifier
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/*created by Gaurav Singh 21-06-20223*/

class NetworkInterceptor @Inject constructor(@ApplicationContextQualifier val context: Context) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        if (!isNetworkConnected()) {
            throw IOException("Network not connected")
        }
        // Proceed with the request and get the response
        val response = chain.proceed(request)

        // Check the response code
        when (response.code) {
            in 200..299 -> {
                // Handle success response
                // Log success, parse response body, etc.
                println("Request succeeded")
            }
            401 -> {
                // Handle unauthorized response
                println("Request Unauthorized")
            }
            in 400..499 -> {
                // Handle client error response
                // Log error, parse error body, etc.
                println("Client error: ${response.code}")
            }
            in 500..599 -> {
                // Handle server error response
                // Log error, parse error body, etc.
                println("Server error: ${response.code}")
            }
            else -> {
                // Handle other response codes
                // Log, handle specific codes, etc.
                println("Unhandled response code: ${response.code}")
            }
        }

        // Return the response
        return response
    }


    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)
        }
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}
