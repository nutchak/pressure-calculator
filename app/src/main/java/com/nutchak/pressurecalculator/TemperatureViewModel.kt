package com.nutchak.pressurecalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


data class TemperatureUnit(
    var temperatureUnit: String
)


data class Temperature(
    var value: String = "",
    var unit: TemperatureUnit = TemperatureUnit("Celsius")
)


var celsius = TemperatureUnit("Celsius")
var fahrenheit = TemperatureUnit("Fahrenheit")


class TemperatureViewModel: ViewModel() {
    private val _temperature: MutableLiveData<Temperature> = MutableLiveData(Temperature())
    val temperature: LiveData<Temperature> = _temperature

    fun onTemperatureChange(newTemperature: String) {
        _temperature.value = _temperature.value?.copy(value = newTemperature)
    }

    fun changeUnit(newUnit: TemperatureUnit) {
        try {
            if (_temperature.value?.unit != newUnit) {
                if (_temperature.value?.unit == celsius) {
                    celsiusToFahrenheit(_temperature.value!!)
                } else {
                    fahrenheitToCelsius(_temperature.value!!)
                }
            }
        }
        catch (e: Exception) {
            println("Error: ${e.message}")
        }
        _temperature.value = _temperature.value?.copy(unit = newUnit)
    }

    fun celsiusToFahrenheit(newTemperature: Temperature) {
        _temperature.value = _temperature.value?.copy(value = (newTemperature.value.toDouble().times(9.0/5.0)
            .plus(32.0)).toString())
    }

    fun fahrenheitToCelsius(newTemperature: Temperature) {
        _temperature.value = _temperature.value?.copy(value = (newTemperature.value.toDouble().minus(32.0)
            .times(5.0/9.0)).toString())
    }
}

