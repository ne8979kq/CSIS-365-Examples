package com.example.mvvm_with_multiple_screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvm_with_multiple_screens.view.AddScreen
import com.example.mvvm_with_multiple_screens.view.ListScreen
import com.example.mvvm_with_multiple_screens.viewmodel.TaskViewModel

//this could all theoretically go in MainActivity as well, but it's not best practice

object Routes {
    const val LIST = "list"
    const val ADD = "add"
}

// the viewmodel object goes in the navigation composable!
@Composable
fun AppNav() {
    val navController = rememberNavController()
    val viewModel: TaskViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.LIST
    ) {
        composable(Routes.LIST) {
            ListScreen(
                //take what you need from viewmodel and pass it as a parameter
                tasks = viewModel.tasks,
                onAddClick = {
                    navController.navigate(Routes.ADD)
                }
            )
        }

        composable(Routes.ADD) {
            AddScreen(
                // again, viewmodel pieces as parameters
                title = viewModel.title,
                note = viewModel.note,
                onTitleChange = viewModel::updateTitle,
                onNoteChange = viewModel::updateNote,
                onSaveClick = {
                    viewModel.saveTask()
                    navController.popBackStack()
                },
                onBackClick = {
                    viewModel.clearForm()
                    navController.popBackStack()
                }
            )
        }
    }
}