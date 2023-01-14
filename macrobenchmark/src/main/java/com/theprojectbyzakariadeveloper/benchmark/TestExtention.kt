package com.theprojectbyzakariadeveloper.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.testNavigationBetweenHomeAndCreatePagesContent() {
    var isHomeScreen = true
    repeat(5) {
        val btn = device.findObject(By.res("create new task btn"))
        val backBtn = device.findObject(By.res("back to home"))
        if (isHomeScreen) {
            isHomeScreen = false
            btn.click()
        } else {
            isHomeScreen = true
            backBtn.click()
        }
    }
}

fun MacrobenchmarkScope.testMenuContent() {
    var isMenuShow = false
    for (i in 0..10) {
        val menu = device.findObject(By.res("click menu"))
        if (isMenuShow) {
            isMenuShow = false
            device.pressBack()
        } else {
            isMenuShow = true
            menu.click()
        }
    }
}

fun MacrobenchmarkScope.testAnimationToolBarContent() {
    var isSearched = false
    for (i in 0..10) {
        val search = device.findObject(By.res("click_search"))
        val hide = device.findObject(By.res("click_hide"))
        if (isSearched) {
            isSearched = false
            hide.click()
        } else {
            isSearched = true
            search.click()
        }
    }
}

fun MacrobenchmarkScope.testNavigationBetweenHomeAndSettingPagesContent() {
    val menu = device.findObject(By.res("click menu"))
    menu.click()
    val itemInMenu = device.findObject(By.hasChild(By.text("Setting")))
    itemInMenu.click()
    val backBtn = device.findObject(By.res("back to home"))
    backBtn.click()
}




fun MacrobenchmarkScope.testClickItemTaskWithAnimationContent() {
    val item1 = device.findObject(By.res("task 1"))
    repeat(10) {
        item1.click()
    }
}

fun MacrobenchmarkScope.testClickItemTaskWithAnimationDetailContent() {
    val item1 = device.findObject(By.res("click for more"))
    repeat(10) {
        item1.click()
        device.wait(Until.hasObject(By.res("card")), 5000)
    }
}


fun MacrobenchmarkScope.testClickInputCategoryDetailContent() {
    val btnCreateTask = device.findObject(By.res("create new task btn"))
    btnCreateTask.click()
    val showCategory = device.findObject(By.res("show categories"))
    showCategory.click()
}

fun MacrobenchmarkScope.testDialogContent() {
    val btnCreateTask = device.findObject(By.res("create new task btn"))
    btnCreateTask.click()
    var showCategory = device.findObject(By.res("add category by dialog"))
    showCategory.click()
    device.pressBack()
}

fun MacrobenchmarkScope.testAnimationAllCategoryContent(){
    val menu = device.findObject(By.res("click menu"))

    menu.click()
    val itemSetting = device.findObject(By.hasChild(By.text("Setting")))
    itemSetting.click()
    repeat(2){
        val allCategories = device.findObject(By.res("all category row"))
        allCategories.click()
    }
}
fun MacrobenchmarkScope.testScrollingContent(){
    val list = device.findObject(By.hasChild(By.res("task 1")))
    list.setGestureMargin(device.displayWidth /10)
    list.fling(Direction.DOWN)
}













