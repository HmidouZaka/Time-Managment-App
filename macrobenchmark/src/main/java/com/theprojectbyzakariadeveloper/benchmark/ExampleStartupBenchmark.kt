package com.theprojectbyzakariadeveloper.benchmark

import androidx.benchmark.macro.*
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "com.theprojectbyzakariadeveloper.timemanagment",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }
    @Test
    fun testAnimationToolBar() = benchmarkRule.measureRepeated(
        packageName = "com.theprojectbyzakariadeveloper.timemanagment",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
        testAnimationToolBarContent()
    }


    @Test
    fun testMenu() = benchmarkRule.measureRepeated(
        packageName = "com.theprojectbyzakariadeveloper.timemanagment",
        metrics = listOf(FrameTimingMetric()),
        iterations = 10,
        startupMode = StartupMode.COLD
    ){
        pressHome()
        startActivityAndWait()
        testMenuContent()
    }

    @Test
    fun testNavigationBetweenHomeAndCreatePages()  = benchmarkRule.measureRepeated(
        packageName = "com.theprojectbyzakariadeveloper.timemanagment",
        metrics = listOf(FrameTimingMetric()),
        iterations = 10,
        startupMode = StartupMode.COLD
    ){
        pressHome()
        startActivityAndWait()
        testNavigationBetweenHomeAndCreatePagesContent()
    }

    @Test
    fun testNavigationBetweenHomeAndSettingPages() = benchmarkRule.measureRepeated(
        packageName = "com.theprojectbyzakariadeveloper.timemanagment",
        metrics = listOf(FrameTimingMetric()),
        iterations = 10,
        startupMode = StartupMode.COLD
    ){
        pressHome()
        startActivityAndWait()
        testNavigationBetweenHomeAndSettingPagesContent()
    }


    @Test
    fun testClickItemTaskWithAnimation() = benchmarkRule.measureRepeated(
        packageName = "com.theprojectbyzakariadeveloper.timemanagment",
        metrics = listOf(FrameTimingMetric()),
        iterations = 10,
        startupMode = StartupMode.COLD
    ){
        pressHome()
        startActivityAndWait()
        testClickItemTaskWithAnimationContent()
    }

    @Test
    fun testClickItemTaskWithAnimationDetail() = benchmarkRule.measureRepeated(
        packageName = "com.theprojectbyzakariadeveloper.timemanagment",
        metrics = listOf(FrameTimingMetric()),
        iterations = 10,
        startupMode = StartupMode.COLD
    ){
        pressHome()
        startActivityAndWait()
        testClickItemTaskWithAnimationDetailContent()
    }

    @Test
    fun testClickInputCategoryDetail() = benchmarkRule.measureRepeated(
        packageName = "com.theprojectbyzakariadeveloper.timemanagment",
        metrics = listOf(FrameTimingMetric()),
        iterations = 10,
        startupMode = StartupMode.COLD
    ){
        pressHome()
        startActivityAndWait()
        testClickInputCategoryDetailContent()
    }
    @Test
    fun testDialog() = benchmarkRule.measureRepeated(
        packageName = "com.theprojectbyzakariadeveloper.timemanagment",
        metrics = listOf(FrameTimingMetric()),
        iterations = 10,
        startupMode = StartupMode.COLD
    ){
        pressHome()
        startActivityAndWait()
        testDialogContent()
    }

    @Test
    fun testAnimationAllCategory() = benchmarkRule.measureRepeated(
        packageName = "com.theprojectbyzakariadeveloper.timemanagment",
        metrics = listOf(FrameTimingMetric()),
        iterations = 10,
        startupMode = StartupMode.COLD
    ){
        pressHome()
        startActivityAndWait()
        testAnimationAllCategoryContent()
    }
    @Test
    fun testScrolling() = benchmarkRule.measureRepeated(
        packageName = "com.theprojectbyzakariadeveloper.timemanagment",
        metrics = listOf(FrameTimingMetric()),
        iterations = 10,
        startupMode = StartupMode.COLD
    ){
        pressHome()
        startActivityAndWait()
        testScrollingContent()
    }
}