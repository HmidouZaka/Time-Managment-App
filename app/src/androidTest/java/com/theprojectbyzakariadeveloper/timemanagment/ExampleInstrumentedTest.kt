package com.theprojectbyzakariadeveloper.timemanagment

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.theprojectbyzakariadeveloper.timemanagment.ui.activity.MainActivity
import com.theprojectbyzakariadeveloper.timemanagment.ui.component.detaile.DetailScreen
import com.theprojectbyzakariadeveloper.timemanagment.ui.component.detaile.Header

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

class ExampleInstrumentedTest {

    @get:Rule
    val f = createAndroidComposeRule<MainActivity>()

    @Before
    fun start(){
        createComposeRule().setContent {
               Header(title = "zakaria")
        }
    }
    @Test
    fun testStart(){
        f.onNodeWithText("zakaria").assertIsEnabled()
    }

}