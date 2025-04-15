package com.neu.mobileapplicationdevelopment202430.UI.team

import android.annotation.SuppressLint
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.neu.mobileapplicationdevelopment202430.fragment.TeamCoverageScreen
import com.neu.mobileapplicationdevelopment202430.pokemon.TeamViewModel
import com.neu.mobileapplicationdevelopment202430.UI.fakes.FakeTeamViewModel
import com.neu.mobileapplicationdevelopment202430.utils.typeColors
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TeamCoverageScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    val fakeViewModel = FakeTeamViewModel()

    @SuppressLint("RememberReturnType")
    private fun setScreenContent() {
        composeTestRule.setContent {
            val viewModelStore = remember { ViewModelStore() }
            val owner = remember {
                object : ViewModelStoreOwner {
                    override val viewModelStore: ViewModelStore = viewModelStore
                }
            }

            CompositionLocalProvider(LocalViewModelStoreOwner provides owner) {
                ViewModelProvider(owner, object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return fakeViewModel as T
                    }
                }).get(TeamViewModel::class.java)

                TeamCoverageScreen()
            }
        }
    }

    @Test
    fun test_coverageScreen_headers_are_displayed() {
        setScreenContent()
        composeTestRule.onNodeWithTag("coverageHeaders").assertIsDisplayed()
        composeTestRule.onNodeWithTag("myTeamTypesTitle").assertTextEquals("My Team Types")
        composeTestRule.onNodeWithTag("allTypesTitle").assertTextEquals("All Types")
    }

    @Test
    fun test_coverageScreen_totalRows_equals_allTypesCount() {
        setScreenContent()
        val totalTypes = typeColors.keys.size
        composeTestRule.onAllNodesWithTag("coverageRow").assertCountEquals(totalTypes)
    }

    @Test
    fun test_coverageScreen_electric_type_is_marked_as_owned() {
        setScreenContent()
        composeTestRule.onNodeWithTag("teamBox_electric").assertIsDisplayed()
        composeTestRule.onNodeWithTag("referenceBox_electric").assertIsDisplayed()
        composeTestRule.onNodeWithTag("teamBox_electric").onChild()
            .assertTextEquals("Electric")
    }

    @Test
    fun test_coverageScreen_water_type_is_marked_as_missing() {
        setScreenContent()
        composeTestRule.onNodeWithTag("teamBox_water").assertIsDisplayed()
        composeTestRule.onNodeWithTag("referenceBox_water").assertIsDisplayed()
        composeTestRule.onNodeWithTag("teamBox_water").onChild()
            .assertTextEquals("Missing")
    }
}
