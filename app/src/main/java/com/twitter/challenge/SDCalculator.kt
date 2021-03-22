package com.twitter.challenge

import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

class SDCalculator @Inject constructor() {

    /**
     * Calculate the standard deviation based on this formula
     * stddev=sqrt(sum(sqr(xi-x))/n-1)
     * n= number of data points
     * x= average/mean of the data
     * xi=each of the values of the data
     * ∑ is the summation operator which is defined as the addition of all the elements of the series.
     * In this case it’s the addition of (xi-x)2for each temperature in the series
     *
     * @throws IllegalArgumentException if the [array] size is less than 2
     */
    fun calculate(array: DoubleArray): Double {
        require(array.size >= 2)

        val mean = array.average()
        val sumMeanSqr =
            array.fold(0.0, { accumulator, next -> accumulator + (next - mean).pow(2.0) })
        return sqrt(sumMeanSqr / (array.size - 1))
    }
}