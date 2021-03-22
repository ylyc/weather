package com.twitter.challenge.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/*
{
  "coord": {
    "lon": -122.42,
    "lat": 37.77
  },
  "weather": {
    "temp": 14.77,
    "pressure": 1007,
    "humidity": 85
  },
  "wind": {
    "speed": 0.51,
    "deg": 284
  },
  "rain": {
   "3h": 1
  },
  "clouds": {
    "cloudiness": 65
  },
  "name": "San Francisco"
}
 */

@JsonClass(generateAdapter = true)
data class CurrentDTO(
    @Json(name = "coord") val coord: CoordDTO?,
    @Json(name = "weather") val weather: WeatherDTO?,
    @Json(name = "wind") val wind: WindDTO?,
    @Json(name = "rain") val rain: RainDTO?,
    @Json(name = "clouds") val clouds: CloudsDTO?,
    @Json(name = "name") val name: String?
)

@JsonClass(generateAdapter = true)
data class CoordDTO(
    @Json(name = "lon") val lon: Float?,
    @Json(name = "lat") val lat: Float?
)

@JsonClass(generateAdapter = true)
data class WeatherDTO(
    @Json(name = "temp") val temp: Float?,
    @Json(name = "pressure") val pressure: Int?,
    @Json(name = "humidity") val humidity: Int?
)

@JsonClass(generateAdapter = true)
data class WindDTO(
    @Json(name = "speed") val speed: Float?,
    @Json(name = "deg") val deg: Int?
)

@JsonClass(generateAdapter = true)
data class RainDTO(
    @Json(name = "3h") val last3hr: Int?
)

@JsonClass(generateAdapter = true)
data class CloudsDTO(
    @Json(name = "cloudiness") val cloudiness: Int?
)