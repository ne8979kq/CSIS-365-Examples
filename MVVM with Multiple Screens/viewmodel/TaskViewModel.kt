package com.example.mvvm_with_multiple_screens.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mvvm_with_multiple_screens.model.Task

class TaskViewModel : ViewModel() {

    val tasks = mutableStateListOf<Task>()

    var title by mutableStateOf("")
        private set

    var note by mutableStateOf("")
        private set

    fun updateTitle(newTitle: String) {
        title = newTitle
    }

    fun updateNote(newNote: String) {
        note = newNote
    }

    fun saveTask() {
        if (title.isBlank() || note.isBlank()) return

        tasks.add(Task(title = title, note = note))
        clearForm()
    }

    fun clearForm() {
        title = ""
        note = ""
    }
}