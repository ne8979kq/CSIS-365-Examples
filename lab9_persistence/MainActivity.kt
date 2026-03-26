package com.example.courses // app package

import android.os.Bundle // activity lifecycle
import androidx.activity.ComponentActivity // base activity
import androidx.activity.compose.setContent // set UI
import androidx.activity.viewModels // ViewModel delegate
import androidx.compose.foundation.layout.* // layout tools
import androidx.compose.foundation.lazy.LazyColumn // list UI
import androidx.compose.foundation.lazy.items // list items
import androidx.compose.material3.* // UI components
import androidx.compose.runtime.* // state
import androidx.compose.ui.Modifier // UI modifier
import androidx.compose.ui.unit.dp // spacing units

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels() // create ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadCourses() // initial load

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), // full screen
                color = MaterialTheme.colorScheme.background // theme color
            ) {

                var code by remember { mutableStateOf("") } // input state
                var credits by remember { mutableStateOf("") } // input state
                var error by remember { mutableStateOf("") } // error text

                Column(
                    modifier = Modifier
                        .fillMaxSize() // full screen
                        .padding(16.dp), // spacing
                    verticalArrangement = Arrangement.spacedBy(12.dp) // gaps
                ) {

                    Text("Course Tracker") // title

                    OutlinedTextField(
                        value = code, // current value
                        onValueChange = {
                            code = it // update state
                            error = "" // clear error
                        },
                        label = { Text("Course Code") }, // label
                        modifier = Modifier.fillMaxWidth() // full width
                    )

                    OutlinedTextField(
                        value = credits, // current value
                        onValueChange = {
                            credits = it // update state
                            error = "" // clear error
                        },
                        label = { Text("Credits") }, // label
                        modifier = Modifier.fillMaxWidth() // full width
                    )

                    if (error.isNotBlank()) {
                        Text(error, color = MaterialTheme.colorScheme.error) // show error
                    }

                    // TODO: Type in the Button code here!

                    Text("Total Credits: ${viewModel.totalCredits}") // show total

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp) // spacing
                    ) {
                        items(viewModel.courses) { course -> // loop data
                            Card(modifier = Modifier.fillMaxWidth()) { // card UI
                                Text(
                                    text = "${course.code} - ${course.credits} credits", // display
                                    modifier = Modifier.padding(16.dp) // spacing
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCourses() // reload on return
    }
}