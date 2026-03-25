package com.example.persistence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNote()

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                var text by rememberSaveable() { mutableStateOf("") }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Persistence Demo", style = MaterialTheme.typography.titleLarge)

                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Type a note") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // this is the user action
                    Button(onClick = { viewModel.saveNote(text) }) {
                        Text("Save")
                    }

                    Text("Saved note:")
                    Text(
                        viewModel.savedNote.ifBlank { "(nothing saved yet)" }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadNote()
    }
}