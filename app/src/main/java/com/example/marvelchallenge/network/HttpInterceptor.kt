package com.example.marvelchallenge.network

import com.example.marvelchallenge.BuildConfig
import com.example.marvelchallenge.di.md5
import okhttp3.Interceptor
import okhttp3.Response

class HttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val timeStamp = System.currentTimeMillis().toString()
        val publicKey = BuildConfig.PUBLIC_KEY
        val privateKey = BuildConfig.PRIVATE_KEY
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