package com.master.androidessentials.utils

import com.master.androidessentials.networking.ApiService
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject


class CustomInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request:Request = chain.request()

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


}
