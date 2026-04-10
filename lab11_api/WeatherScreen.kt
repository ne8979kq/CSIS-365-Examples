package com.example.lab11

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeatherScreen() { // first, we declare two variables!
    val locationInput = remember { mutableStateOf("") } // locationInput starts empty, weatherResult does not
    val weatherResult = remember { mutableStateOf("Weather information will appear here.") }

    Column( // column to arrange child composables in a vertical stack
        modifier = Modifier
            .fillMaxSize() // fills the max width/height of the screen
            .padding(16.dp), // padding of 16 on each side
        verticalArrangement = Arrangement.Top // keeps content close to the top
    ) { // inside the column we have multiple components
        Text("Lab 11: Forecast Fixer") // a Text with a title

        Spacer(modifier = Modifier.height(16.dp)) // a Spacer to add vertical space between components

        OutlinedTextField( // a TextField for user input with 4 parameters
            value = locationInput.value, // this is the value shown in the box, starts empty
            onValueChange = { locationInput.value = it }, // updates state when user types
            label = { Text("Enter a location" ) }, // label inside/above box
            modifier = Modifier.fillMaxWidth() // another modifier to stretch box across the screen
        )

        Spacer(modifier = Modifier.height(16.dp)) // more space between components

        Button(onClick = { }) { // nonfunctional button
            Text("Get Weather") // and a label for that button
        }

        Spacer(modifier = Modifier.height(24.dp)) // even more space, this time a bit bigger

        Text(weatherResult.value) // and finally, Text to display our weatherResult string
    }
}