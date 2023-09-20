package com.nutchak.pressurecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.TextButton
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nutchak.pressurecalculator.ui.theme.PressureCalculatorTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PressureCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MyApp()
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(viewModel: TemperatureViewModel = viewModel()) {
    MainScreen()

}


@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun MyAppPreview() {
    PressureCalculatorTheme {
        MyApp()
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(temperatureViewModel: TemperatureViewModel = viewModel()) {
    val temperature: String by temperatureViewModel.temperature.observeAsState("")
    Column {
        InputField(
            temperature = temperature,
            onTemperatureChange = { temperatureViewModel.onTemperatureChange(it) }
        )
        DropDownTemperature()
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen(temperatureViewModel: TemperatureViewModel = viewModel()) {
    PressureCalculatorTheme {
        MainScreen(temperatureViewModel = temperatureViewModel)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    temperature: String,
    onTemperatureChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = temperature,
            onValueChange = onTemperatureChange,
            label = { Text("Enter a temperature") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "Temperature: $temperature")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInputField() {
    PressureCalculatorTheme {
        InputField(
            temperature = "",
            onTemperatureChange = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownTemperature(
    modifier: Modifier = Modifier
) {
    val temperatureList = arrayOf("Celsius", "Fahrenheit")
    var selectedTemperatureUnit = remember { mutableStateOf(temperatureList[0]) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Column(

    ) {
        Box(
            modifier = modifier
        ) {
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = !isExpanded }
            ) {
                TextField(
                    value = selectedTemperatureUnit.value,
                    onValueChange = {  },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                    modifier = modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    temperatureList.forEach { selectedOption ->
                        DropdownMenuItem(
                            text = { Text(text = selectedOption) },
                            onClick = {
                                selectedTemperatureUnit.value = selectedOption
                                isExpanded = false
                            },
                            enabled = true
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "Temperature Unit: ${selectedTemperatureUnit.value}")
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewDropDownTemperature() {
    PressureCalculatorTheme {
        DropDownTemperature()
    }
}