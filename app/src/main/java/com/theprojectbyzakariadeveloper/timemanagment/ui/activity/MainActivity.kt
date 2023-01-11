package com.theprojectbyzakariadeveloper.timemanagment.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.FileProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.theprojectbyzakariadeveloper.timemanagment.BuildConfig
import com.theprojectbyzakariadeveloper.timemanagment.model.Category
import com.theprojectbyzakariadeveloper.timemanagment.model.Task
import com.theprojectbyzakariadeveloper.timemanagment.ui.component.detaile.DetailScreen
import com.theprojectbyzakariadeveloper.timemanagment.ui.component.home.MainScreen
import com.theprojectbyzakariadeveloper.timemanagment.ui.component.setting.SettingScreen
import com.theprojectbyzakariadeveloper.timemanagment.ui.theme.TimeManagmentTheme
import com.theprojectbyzakariadeveloper.timemanagment.utils.ResultState
import com.theprojectbyzakariadeveloper.timemanagment.utils.Screens
import com.theprojectbyzakariadeveloper.timemanagment.viewmodel.MainViewModel
import java.io.File
import java.util.*

class MainActivity : ComponentActivity() {

    lateinit var viewModel: MainViewModel

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory(this@MainActivity.application).create(MainViewModel::class.java)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition{
                viewModel.tasks.value  is ResultState.Loading
            }
        }
            setContent {
                val navController = rememberNavController()
                TimeManagmentTheme {
                    NavHost(navController = navController, startDestination = Screens.Home.name) {
                        composable(route = Screens.Home.name) {
                            MainScreen(navController = navController, viewModel = viewModel,this@MainActivity)
                        }
                        composable(route = Screens.Detail.name) {
                            DetailScreen(
                                mainActivity = this@MainActivity,
                                viewModel = viewModel,
                                navController = navController,
                                saveTask = ::saveTask,
                                saveCategory = ::saveCategory
                            )
                        }
                        composable(Screens.EditTask.name) {
                            var task =
                                navController.previousBackStackEntry?.savedStateHandle?.get<Task>("task")
                            DetailScreen(
                                mainActivity = this@MainActivity,
                                viewModel = viewModel,
                                navController = navController,
                                saveTask = ::upDateTask,
                                saveCategory = ::saveCategory,
                                taskEdit = task
                            )
                        }
                        composable(Screens.Setting.name) {
                            SettingScreen(navHostController = navController, viewModel = viewModel)
                        }
                    }
            }
        }



    }

    private fun saveTask(task: Task) {
        //Toast.makeText(this, task.task, Toast.LENGTH_SHORT).show()
        viewModel.insert(task)
    }

    private fun saveCategory(category: Category) {
        if (category.name.isNotEmpty()) {
            viewModel.insert(category)
        } else {
            Toast.makeText(this, "enter name for category", Toast.LENGTH_SHORT).show()
        }
    }

    private fun upDateTask(task: Task) {
        viewModel.update(task)
    }

    fun shareApp(){
        try {
            val pm: PackageManager = packageManager
            val ai: ApplicationInfo = pm.getApplicationInfo(packageName, 0)
            val intentSend = Intent(Intent.ACTION_SEND)
            val fileDir = File(ai.sourceDir)
            val file = FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID+".provider",fileDir)
            intentSend.putExtra(Intent.EXTRA_STREAM,file)
            intentSend.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            intentSend.type = "application/vnd.android.package-archive"
            startActivity(Intent.createChooser(intentSend, "PersianCoders"))
        } catch (e: java.lang.Exception) {
            Log.e("ShareApp", e.message.toString())
        }

    }
}
