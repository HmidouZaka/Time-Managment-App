package com.theprojectbyzakariadeveloper.timemanagment.viewmodel

import android.app.Application
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.theprojectbyzakariadeveloper.timemanagment.data.local.database.MyDataBase
import com.theprojectbyzakariadeveloper.timemanagment.model.Category
import com.theprojectbyzakariadeveloper.timemanagment.model.Task
import com.theprojectbyzakariadeveloper.timemanagment.repository.MainRepository
import com.theprojectbyzakariadeveloper.timemanagment.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File


class MainViewModel(application: Application) : AndroidViewModel(application){
    val applicationTask = application
    private val _result = MutableStateFlow<ResultState<List<Task>>>(ResultState.Loading())
    val tasks = _result.asStateFlow()
    private lateinit var repository: MainRepository
    init {
        val dataBase = MyDataBase.getDatabase(this.getApplication<Application>().applicationContext)
        repository = MainRepository(dataBase)
        viewModelScope.launch {
            repository.tasks.collectLatest {
                try {
                    _result.emit(ResultState.Success(result = it))
                }catch (ex:Exception){
                    _result.emit(ResultState.Error(errorMessage = ex.message))
                }
            }
        }

    }

    val categories = repository.categories




    fun insert(category: Category){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCategory(category)
        }
    }


    fun insert(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }
    fun delete(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeTask(task)
        }
    }
    fun update(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.upDateTask(task)
        }
    }
    fun delete(category: Category){
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeCtegory(category)
        }
    }


    fun findTask(task: String){
        viewModelScope.launch(Dispatchers.IO) {
            _result.emit(ResultState.Loading())
            val tasks = repository.findTask(task)
            tasks.collectLatest {
                try {
                    _result.emit(ResultState.Success(result = it))
                }catch (ex:Exception){
                    _result.emit(ResultState.Error(errorMessage = ex.message))
                }
            }
        }
    }



}