package com.example.persistence

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = NoteDatabase.get(app).noteDao()

    var savedNote by mutableStateOf("")
        private set

    fun loadNote() {
        viewModelScope.launch {
            savedNote = dao.getNote()?.content ?: ""
        }
    }

    fun saveNote(text: String) {
        // this is where the app logic happens
        // this launches a coroutine so the app doesn't freeze
        viewModelScope.launch {
            dao.saveNote(Note(content = text))
            savedNote = text
        }
    }
}