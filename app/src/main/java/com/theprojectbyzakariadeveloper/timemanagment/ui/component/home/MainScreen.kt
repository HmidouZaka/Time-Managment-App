package com.theprojectbyzakariadeveloper.timemanagment.ui.component.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.theprojectbyzakariadeveloper.timemanagment.model.Task
import com.theprojectbyzakariadeveloper.timemanagment.ui.activity.MainActivity
import com.theprojectbyzakariadeveloper.timemanagment.utils.OptionsMenu
import com.theprojectbyzakariadeveloper.timemanagment.utils.ResultState
import com.theprojectbyzakariadeveloper.timemanagment.utils.Screens
import com.theprojectbyzakariadeveloper.timemanagment.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    mainActivity: MainActivity
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToolBar(ocClickItemMenu = {
                when (it) {
                    OptionsMenu.Setting -> {
                        navController.navigate(Screens.Setting.name)
                    }
                    OptionsMenu.SendApp -> {
                        mainActivity.shareApp()
                    }
                }
            }, onSearch = {
                viewModel.findTask(it)
            }
            )
        }
    ) {
        val resultState = viewModel.tasks.collectAsState()
        when (resultState.value) {
            is ResultState.Loading -> {

            }
            is ResultState.Error -> {
                val error = resultState.value as ResultState.Error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = error.errorMessage ?: "error")
                }
            }
            is ResultState.Success -> {
                val tasks = resultState.value.data!! as List<Task>
                if (tasks.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        contentPadding = PaddingValues(vertical = 10.dp),
                        modifier = Modifier
                            .testTag("list of tasks")
                            .fillMaxSize()
                            .animateContentSize(
                                spring(
                                    Spring.DampingRatioHighBouncy, Spring.StiffnessHigh
                                )
                            )
                    ) {
                        items(tasks, { it.id }) { task ->
                            TaskCard(note = task, viewModel) {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "task",
                                    it
                                )
                                navController.navigate(Screens.EditTask.name)
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No Tasks")
                    }
                }

            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.Detail.name)
                },
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.BottomEnd)
                    .testTag("create new task btn"),
                backgroundColor = Color(0xFF03A9F4)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add an task",
                    tint = Color.White
                )
            }
        }
    }

}


