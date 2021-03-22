package com.twitter.challenge.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.twitter.challenge.SDCalculator
import com.twitter.challenge.data.Weather
import com.twitter.challenge.di.AppModule
import com.twitter.challenge.di.DaggerAppComponent
import com.twitter.challenge.manager.WeatherManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

private val TAG = MainViewModel::class.simpleName

class MainViewModel(application: Application) :
    AndroidViewModel(application) {
    @Inject
    lateinit var weatherManager: WeatherManager

    @Inject
    lateinit var sdCalculator: SDCalculator

    val currentWeather: MutableLiveData<Weather> = MutableLiveData()

    val temperatureSDInNext5Days: MutableLiveData<Double> = MutableLiveData()


    private val compositeDisposable = CompositeDisposable()

    init {
        DaggerAppComponent.builder().appModule(AppModule(getApplication()))
            .build()
            .inject(this)
    }

    fun getNext5DayTemperatureStandardDeviation() {
        weatherManager.getNext5DayWeather()
            .map {
                it.map { weather ->
                    weather.temperatureC
                }.map { temperature ->
                    temperature?.toDouble() ?: throw IOException("Temperature not available.")
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    temperatureSDInNext5Days.value = sdCalculator.calculate(it.toDoubleArray())
                },
                { throwable ->
                    Log.d(TAG, throwable.toString())
                    temperatureSDInNext5Days.value = null
                }).also {
                compositeDisposable.addAll(it)
            }
    }

    fun getWeatherData() {
        weatherManager.getCurrentWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { weather ->
                    currentWeather.value = weather
                },
                { throwable ->
                    Log.d(TAG, throwable.toString())
                    currentWeather.value = null
                }).also {
                compositeDisposable.addAll(it)
            }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}