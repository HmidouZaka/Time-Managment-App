package com.theprojectbyzakariadeveloper.timemanagment.utils

sealed class ResultState<T>(
    var data:T?= null,
    var message:String?= null
){
    data class Error<T>(val errorMessage:String?):ResultState<T>(message = errorMessage)
    data class Success<T>(val result:T):ResultState<T>(data = result)
    class Loading<T>:ResultState<T>()
}