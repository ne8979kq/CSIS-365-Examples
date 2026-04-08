package com.example.connectingtotheinternet.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.connectingtotheinternet.ui.components.ScreenCard
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

data class DogResponse(
    val message: String,
    val status: String
)

interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDog(): DogResponse
}

@Composable
fun DogScreen() {
    var dogUrl by remember { mutableStateOf<String?>(null) }
    var errorText by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    val moshi = remember {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    val api = remember {
        Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(DogApiService::class.java)
    }

    ScreenCard(title = "Random Dog API") {
        Button(onClick = {
            scope.launch {
                try {
                    errorText = null
                    dogUrl = api.getRandomDog().message
                } catch (e: Exception) {
                    errorText = e.message
                }
            }
        }) {
            Text("Fetch Dog")
        }

        dogUrl?.let {
            AsyncImage(
                model = it,
                contentDescription = "Random dog",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }

        errorText?.let {
            Text("Error: $it")
        }
    }
}