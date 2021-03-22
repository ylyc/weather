package com.twitter.challenge.di

import android.content.Context
import com.twitter.challenge.TemperatureConverter
import com.twitter.challenge.data.api.WeatherApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://twitter-code-challenge.s3.amazonaws.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }
}