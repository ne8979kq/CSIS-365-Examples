package com.example.courses // app package

import android.app.Application // app context access
import androidx.compose.runtime.* // state management
import androidx.lifecycle.AndroidViewModel // lifecycle ViewModel
import androidx.lifecycle.viewModelScope // coroutine scope
import kotlinx.coroutines.launch // coroutine builder

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = CourseDatabase.getDatabase(app).courseDao() // get DAO

    var courses by mutableStateOf<List<Course>>(emptyList()) // list state
        private set // read-only outside

    var totalCredits by mutableIntStateOf(0) // total credits
        private set // read-only outside

    // TODO: Type in the two functions provided in the lab
    // one here

    // one here

}