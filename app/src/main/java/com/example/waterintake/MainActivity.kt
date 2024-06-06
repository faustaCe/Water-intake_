package com.example.waterintake

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.waterintake.ui.theme.WaterIntakeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterIntakeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WaterIntakeCalculator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun WaterIntakeCalculator(modifier: Modifier = Modifier) {
    val height = rememberSaveable { mutableStateOf("") }
    val weight = rememberSaveable { mutableStateOf("") }
    val waterIntake = rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {
        Text(text = "Enter your height (cm):")
        Row {
            TextField(
                value = height.value,
                onValueChange = { height.value = it },
                label = { Text("Height") }
            )
        }
        Text(text = "Enter your weight (kg):")
        Row {
            TextField(
                value = weight.value,
                onValueChange = { weight.value = it },
                label = { Text("Weight") }
            )
        }
        Button(onClick = {
            val heightValue = height.value.toDoubleOrNull()
            val weightValue = weight.value.toDoubleOrNull()
            if (heightValue != null && weightValue != null) {
                val calculatedWaterIntake = calculateWaterIntake(heightValue, weightValue)
                waterIntake.value = "Your daily water intake is: $calculatedWaterIntake ml"

                // Save values to SharedPreferences
                val sharedPreferences = getSharedPreferences("water_intake_prefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("height", height.value)
                editor.putString("weight", weight.value)
                editor.apply()
            } else {
                waterIntake.value = "Invalid input"
            }
        }) {
            Text(text = "Calculate")
        }
        Text(text = waterIntake.value)
    }
}

private fun Any.apply() {
    TODO("Not yet implemented")
}

private fun Any.putString(s: String, value: String) {

}

private fun Unit.edit(): Any {
    TODO("Not yet implemented")
}

fun getSharedPreferences(s: String, modePrivate: Any) {
    TODO("Not yet implemented")
}

private fun calculateWaterIntake(height: Double, weight: Double): Int {
    // formula to calculate daily water intake based on height and weight
    val waterIntake = (height * weight) / 30
    return waterIntake.toInt()
}

@Preview(showBackground = true)
@Composable
fun WaterIntakeCalculatorPreview() {
    WaterIntakeTheme {
        WaterIntakeCalculator()
    }
}