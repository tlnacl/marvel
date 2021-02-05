package com.example.marvelchallenge.network

import com.example.marvelchallenge.BuildConfig
import com.example.marvelchallenge.di.md5
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.lang.IllegalStateException

class HttpInterceptor : Interceptor {
    private val defaultApiKey = "replace your key"

    override fun intercept(chain: Interceptor.Chain): Response {
        val timeStamp = System.currentTimeMillis().toString()
        val publicKey = BuildConfig.PUBLIC_KEY
        val privateKey = BuildConfig.PRIVATE_KEY
        if (publicKey == defaultApiKey || privateKey == defaultApiKey) throw IOException("Please replace your PUBLIC_KEY and PRIVATE_KEY in build.gradle")
        val hash = md5(timeStamp + privateKey + publicKey)
        val httpUrl = chain.request().url.newBuilder()
            .addQueryParameter("ts", timeStamp)
            .addQueryParameter("apikey", publicKey)
            .addQueryParameter("hash", hash)
            .build()

        val request = chain.request().newBuilder().url(httpUrl).build()
        return chain.proceed(request)
    }
}