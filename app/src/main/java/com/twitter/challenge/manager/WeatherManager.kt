package com.twitter.challenge.manager

import com.twitter.challenge.data.Weather
import com.twitter.challenge.data.api.WeatherApi
import com.twitter.challenge.data.mapper.CurrentWeatherMapper
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WeatherManager @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherMapper: CurrentWeatherMapper
) {

    /**
     * Get the weather for today.
     */
    fun getCurrentWeather(): Single<Weather> {
        return Single.fromObservable(
            weatherApi.getCurrentWeather()
                .map {
                    weatherMapper.mapToWeather(it)
                }
        )
    }

    /**
     * Get the weather for the next 5 days starting tomorrow.
     */
    fun getNext5DayWeather(): Single<List<Weather>> {
        val future1 = weatherApi.getFutureWeather(1)
        val future2 = weatherApi.getFutureWeather(2)
        val future3 = weatherApi.getFutureWeather(3)
        val future4 = weatherApi.getFutureWeather(4)
        val future5 = weatherApi.getFutureWeather(5)

        return Single.fromObservable(
            Observable.zip(
                future1,
                future2,
                future3,
                future4,
                future5,
                { t1, t2, t3, t4, t5 ->
                    listOf(t1, t2, t3, t4, t5)
                })
                .map { dtos ->
                    dtos.map { weatherMapper.mapToWeather(it) }
                }
        )
    }
}