package com.example.lab10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Lab10Demo()
                }
            }
        }
    }
}

data class GalleryItem(
    val title: String,
    val category: String,
    val emoji: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lab10Demo() {
    val items = listOf(  
        GalleryItem("Spyro", "Games", "🐉"),
        GalleryItem("Zelda", "Games", "🗡️"),
        GalleryItem("Cat", "Animals", "🐱"),
        GalleryItem("Dog", "Animals", "🐶"),
        GalleryItem("Pizza", "Food", "🍕"),
        GalleryItem("Burger", "Food", "🍔")
    )

    val categories = listOf("Games", "Animals", "Food")
    var selected by remember { mutableStateOf(setOf<String>()) }

    val filteredItems = if (selected.isEmpty()) {
        items
    } else {
        items.filter { it.category in selected }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Image Grid with Chip Filter", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            categories.forEach { category ->
                FilterChip(
                    selected = category in selected,
                    onClick = {
                        selected = if (category in selected) {
                            selected - category
                        } else {
                            selected + category
                        }
                    },
                    label = { Text(category) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredItems) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(item.emoji, style = MaterialTheme.typography.displayMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(item.title)
                        Text(item.category, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

