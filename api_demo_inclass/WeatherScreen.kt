package com.example.connectingtotheinternet.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.example.connectingtotheinternet.BuildConfig
import com.example.connectingtotheinternet.ui.components.ScreenCard
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class GeoResult(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String? = null
)

data class WeatherResponse(
    val name: String,
    val main: MainInfo,
    val weather: List<WeatherCondition>
)

data class MainInfo(
    val temp: Double,
    val feels_like: Double
)

data class WeatherCondition(
    val description: String
)

interface WeatherApiService {
    @GET("geo/1.0/direct")
    suspend fun getCoordinates(
        @Query("q") city: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String
    ): List<GeoResult>

    @GET("data/2.5/weather")
    suspend fun getWeatherByCoords(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "imperial"
    ): WeatherResponse
}

@Composable
fun WeatherScreen() {
    var city by remember { mutableStateOf("Fargo") }
    var weatherText by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    val moshi = remember {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    val api = remember {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(WeatherApiService::class.java)
    }

    ScreenCard(title = "Weather API Demo") {
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") }
        )

        Button(onClick = {
            scope.launch {
                try {
                    errorText = null

                    // STEP 1: get coordinates
                    val geoResults = api.getCoordinates(
                        city = city.trim(),
                        apiKey = BuildConfig.OPENWEATHER_API_KEY
                    )

                    if (geoResults.isEmpty()) {
                        errorText = "City not found"
                        return@launch
                    }

                    val location = geoResults.first()

                    // STEP 2: get weather using lat/lon
                    val weather = api.getWeatherByCoords(
                        lat = location.lat,
                        lon = location.lon,
                        apiKey = BuildConfig.OPENWEATHER_API_KEY
                    )

                    weatherText =
                        "City: ${weather.name}\n" +
                                "Temp: ${weather.main.temp}°F\n" +
                                "Feels like: ${weather.main.feels_like}°F\n" +
                                "Condition: ${weather.weather.firstOrNull()?.description}"

                } catch (e: Exception) {
                    errorText = e.message
                }
            }
        }) {
            Text("Get Weather")
        }

        if (weatherText.isNotBlank()) {
            Text(weatherText)
        }

        errorText?.let {
            Text("Error: $it")
        }
    }
}