package com.twitter.challenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.twitter.challenge.R
import com.twitter.challenge.data.Weather
import com.twitter.challenge.databinding.MainFragmentBinding

private const val KEY_TEMPERATURE_SD_NEXT_5_DAYS = "temperatureSDInNext5Days"
private const val KEY_CURRENT_WEATHER = "currentWeather"

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    private var weather: Weather? = null
    private var sdTemperature: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_CURRENT_WEATHER)) {
            weather = savedInstanceState.getParcelable(KEY_CURRENT_WEATHER)
            updateWeather()
        } else {
            viewModel.getWeatherData()
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(
                KEY_TEMPERATURE_SD_NEXT_5_DAYS
            )) {
            sdTemperature = savedInstanceState.getDouble(KEY_TEMPERATURE_SD_NEXT_5_DAYS)
            updateSD()
        }
    }

    private fun setupObservers() {
        viewModel.currentWeather.observe(viewLifecycleOwner, {
            weather = it
            updateWeather()
        })

        viewModel.temperatureSDInNext5Days.observe(viewLifecycleOwner, {
            sdTemperature = it
            updateSD()
        })
    }

    private fun updateWeather() {
        weather.let {
            if (it == null) {
                binding.temperatureTv.text = requireContext().getString(R.string.data_not_available)
                binding.windSpeedTv.text = ""
                binding.cloudIv.visibility = View.GONE
            } else {
                binding.temperatureTv.text =
                    "${it.temperatureC.toString()}C / ${it.temperatureF.toString()}F"
                binding.windSpeedTv.text = "${it.windSpeed.toString()} meter/sec"
                if (it.cloudPercent ?: 0 > 50) {
                    binding.cloudIv.visibility = View.VISIBLE
                } else {
                    binding.cloudIv.visibility = View.GONE
                }
            }
        }
    }

    private fun updateSD() {
        sdTemperature.let {
            if (it == null) {
                binding.sdTemp5daysTv.text = getString(R.string.data_not_available)
            } else {
                binding.sdTemp5daysTv.text = "SD = $it C"
            }
        }
    }

    private fun setupViews() {
        binding.next5DaysBtn.setOnClickListener {
            viewModel.getNext5DayTemperatureStandardDeviation()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        sdTemperature?.let { outState.putDouble(KEY_TEMPERATURE_SD_NEXT_5_DAYS, it) }
        weather?.let { outState.putParcelable(KEY_CURRENT_WEATHER, it) }
    }
}