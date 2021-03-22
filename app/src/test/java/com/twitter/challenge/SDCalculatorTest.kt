package com.twitter.challenge

import org.assertj.core.api.Java6Assertions.assertThat
import org.assertj.core.api.Java6Assertions.within
import org.junit.Before
import org.junit.Test

class SDCalculatorTest {

    private lateinit var sdCalculator: SDCalculator

    @Before
    fun setUp() {
        sdCalculator = SDCalculator()
    }

    @Test(expected = IllegalArgumentException::class)
    fun calculate_withArrayLessThan2_throwsException() {
        sdCalculator.calculate(DoubleArray(1))
    }

    @Test
    fun calculate_withValues_returnSdWithinHundredthPrecision() {
        val precision = within(0.01)

        // @formatter:off
        assertThat(sdCalculator.calculate(arrayOf(0.0, 0.0, 0.0).toDoubleArray())).isEqualTo(0.0, precision)
        assertThat(sdCalculator.calculate(arrayOf(10.0, -12.0, -23.0, 23.0, 16.3).toDoubleArray())).isEqualTo(19.537092926022, precision)
        assertThat(sdCalculator.calculate(arrayOf(13.31, 13.31).toDoubleArray())).isEqualTo(0.0, precision)
        assertThat(
            sdCalculator.calculate(arrayOf(10.0, 12.0, 23.0, 23.0, 16.0, 23.0, 21.0, 16.0).toDoubleArray())
        ).isEqualTo(5.2372293656638, precision)
        assertThat(
            sdCalculator.calculate(arrayOf(11002.0, 120311.3, 222323.0, 23333.7, 16.333, 23.22221, 21.3, 16.5).toDoubleArray())
        ).isEqualTo(81763.809510339, precision)
        // @formatter:on
    }
}