package com.twitter.challenge.data

import android.os.Parcel
import android.os.Parcelable

/**
 * Weather of this [location].
 *
 * @param temperatureC temperature in Celsius
 * @param temperatureF temperature in Fahrenheit
 * @param windSpeed Wind speed. Metric: meter/sec
 * @param cloudPercent Cloudiness, %
 */
data class Weather(
    val temperatureC: Float?,
    val temperatureF: Float?,
    val windSpeed: Float?,
    val cloudPercent: Int?,
    val location: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(temperatureC)
        parcel.writeValue(temperatureF)
        parcel.writeValue(windSpeed)
        parcel.writeValue(cloudPercent)
        parcel.writeString(location)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }
}
