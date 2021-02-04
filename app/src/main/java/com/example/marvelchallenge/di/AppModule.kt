package com.example.marvelchallenge.di

import android.content.Context
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.example.marvelchallenge.BuildConfig
import com.example.marvelchallenge.network.HttpInterceptor
import com.example.marvelchallenge.network.MarvelApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun provideRandomUserApi(): MarvelApi {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(HttpInterceptor())

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                Timber.tag("OkHttp").d(message)
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }
        val okHttpClient = httpClientBuilder.build()

        val imageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .okHttpClient(okHttpClient)
            .build()
        Coil.setImageLoader(imageLoader)

        val restAdapter = Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return restAdapter.create(MarvelApi::class.java)
    }
}