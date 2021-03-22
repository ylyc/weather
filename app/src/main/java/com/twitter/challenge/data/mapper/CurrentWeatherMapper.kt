package com.twitter.challenge.data.mapper

import com.twitter.challenge.TemperatureConverter
import com.twitter.challenge.data.Weather
import com.twitter.challenge.data.dto.CurrentDTO
import javax.inject.Inject

class CurrentWeatherMapper @Inject constructor() {

    /**
     * Convert [CurrentDTO] to [Weather].
     */
    fun mapToWeather(currentDTO: CurrentDTO): Weather {
        return Weather(
            location = currentDTO.name.orEmpty(),
            temperatureC = currentDTO.weather?.temp,
            temperatureF = currentDTO.weather?.temp?.let {
                TemperatureConverter.celsiusToFahrenheit(
                    it
                )
            },
            windSpeed = currentDTO.wind?.speed,
            cloudPercent = currentDTO.clouds?.cloudiness
        )
    }
}