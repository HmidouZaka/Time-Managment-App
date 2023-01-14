package com.theprojectbyzakariadeveloper.benchmark

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith

@OptIn(ExperimentalBaselineProfilesApi::class)
@RunWith(AndroidJUnit4::class)
class BaseLineProfile {
    @get:Rule
    val baseLineProfile = BaselineProfileRule()


    fun startBaseLineProfile() =
        baseLineProfile.collectBaselineProfile(
            packageName = "com.theprojectbyzakariadeveloper.timemanagment"
        ) {
            pressHome()
            startActivityAndWait()
            testNavigationBetweenHomeAndCreatePagesContent()
            testNavigationBetweenHomeAndSettingPagesContent()
            testMenuContent()
            testAnimationAllCategoryContent()
            testDialogContent()
            testClickItemTaskWithAnimationDetailContent()
            testClickInputCategoryDetailContent()
            testScrollingContent()
            testClickItemTaskWithAnimationContent()
            testAnimationToolBarContent()
        }
}