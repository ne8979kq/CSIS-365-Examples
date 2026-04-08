package com.example.connectingtotheinternet.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.connectingtotheinternet.ui.components.ScreenCard
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@JsonClass(generateAdapter = true)
data class PokemonListResponse(
    val results: List<PokemonListItem>
)

@JsonClass(generateAdapter = true)
data class PokemonListItem(
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    val name: String,
    val sprites: PokemonSprites
)

@JsonClass(generateAdapter = true)
data class PokemonSprites(
    val front_default: String?
)

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 10
    ): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String
    ): PokemonResponse
}

@Composable
fun PokemonScreen() {
    var pokemonListText by remember { mutableStateOf("") }
    var selectedName by remember { mutableStateOf("pikachu") }
    var spriteUrl by remember { mutableStateOf<String?>(null) }
    var errorText by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    val moshi = remember {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    val api = remember {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PokemonApiService::class.java)
    }

    ScreenCard(title = "PokéAPI Demo") {
        Button(onClick = {
            scope.launch {
                try {
                    errorText = null
                    val result = api.getPokemonList()
                    pokemonListText = result.results.joinToString { it.name }
                } catch (e: Exception) {
                    errorText = e.message
                }
            }
        }) {
            Text("Load Pokémon")
        }

        if (pokemonListText.isNotBlank()) {
            Text("List: $pokemonListText")
        }

        OutlinedTextField(
            value = selectedName,
            onValueChange = { selectedName = it.lowercase() },
            label = { Text("Pokémon name") }
        )

        Button(onClick = {
            scope.launch {
                try {
                    errorText = null
                    spriteUrl = api.getPokemonByName(selectedName.trim()).sprites.front_default
                } catch (e: Exception) {
                    errorText = e.message
                }
            }
        }) {
            Text("Get Sprite")
        }

        spriteUrl?.let {
            AsyncImage(
                model = it,
                contentDescription = selectedName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
        }

        errorText?.let {
            Text("Error: $it")
        }
    }
}