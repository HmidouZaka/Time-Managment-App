package com.theprojectbyzakariadeveloper.timemanagment.utils

import com.theprojectbyzakariadeveloper.timemanagment.model.Task

fun Task?.isNull(): Boolean {
    return this == null
}