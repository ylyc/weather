package com.twitter.challenge.data.api

import com.twitter.challenge.data.dto.CurrentDTO
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {
    @GET("current.json")
    fun getCurrentWeather(): Observable<CurrentDTO>

    @GET("future_{day}.json")
    fun getFutureWeather(@Path("day") nthDay: Int): Observable<CurrentDTO>
}
