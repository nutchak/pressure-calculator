package com.nutchak.pressurecalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


data class TemperatureList(
    var temperatureList: List<TemperatureUnit>
)

data class TemperatureUnit(
    var temperatureUnit: String
)

data class Temperature(
    var value: Double? = 0.0,
    var unit: TemperatureUnit = TemperatureUnit("Celsius")
)


var celsius = TemperatureUnit("Celsius")
var fahrenheit = TemperatureUnit("Fahrenheit")
var temperatureList = TemperatureList(listOf(celsius, fahrenheit))

class TemperatureViewModel: ViewModel() {
    private val _temperature: MutableLiveData<String> = MutableLiveData("")
    val temperature: LiveData<String> = _temperature

    fun onTemperatureChange(newTemperature: String) {
        _temperature.value = newTemperature
    }

}

