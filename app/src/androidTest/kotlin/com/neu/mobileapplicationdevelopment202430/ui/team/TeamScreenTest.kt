package com.neu.mobileapplicationdevelopment202430.UI.team

import android.annotation.SuppressLint
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.neu.mobileapplicationdevelopment202430.fragment.TeamScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.neu.mobileapplicationdevelopment202430.pokemon.TeamViewModel
import com.neu.mobileapplicationdevelopment202430.UI.fakes.FakeTeamViewModel

@RunWith(AndroidJUnit4::class)
class TeamScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    val fakeViewModel = FakeTeamViewModel()

    @Composable
    fun TestScreen() {
        val navController = rememberNavController()
        TeamScreen(navController = navController)
    }

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

                TeamScreen(navController = rememberNavController())
            }
        }
    }

    @Test
    fun teamHeader_IsDisplayed() {
        composeTestRule.setContent { TestScreen() }
        composeTestRule.onNodeWithTag("teamHeader").assertExists()
        composeTestRule.onNodeWithTag("teamScreenTitle").assertExists().assertTextEquals("My Team")
    }


    @Test
    fun teamList_IsDisplayed() {
        composeTestRule.setContent { TestScreen() }
        composeTestRule.onNodeWithTag("teamList").assertExists()
    }

    @Test
    fun coverageButton_IsDisplayed() {
        composeTestRule.setContent { TestScreen() }
        composeTestRule.onNodeWithTag("checkCoverageButton").assertExists()
    }

    @Test
    fun placeholderCards_AreDisplayedWhenTeamIsEmpty() {
        composeTestRule.setContent { TestScreen() }
        composeTestRule.onAllNodesWithTag("placeholderCard").assertCountEquals(6)
    }

    @Test
    fun teamScreen_shows_one_card_and_five_placeholders() {
        setScreenContent()
        composeTestRule.onNodeWithTag("teamPokemonCard_25").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("placeholderCard").assertCountEquals(5)
    }

    @Test
    fun pokemonCard_displays_correct_info() {
        setScreenContent()
        composeTestRule.onNodeWithText("Pikachu").assertIsDisplayed()
        composeTestRule.onNodeWithText("Electric").assertIsDisplayed()
    }

}
