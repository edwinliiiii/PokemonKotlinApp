package com.neu.mobileapplicationdevelopment202430.UI

import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.neu.mobileapplicationdevelopment202430.AppNav
import com.neu.mobileapplicationdevelopment202430.utils.NavigationLabel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppNavTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val randomLabel = NavigationLabel.Random.label
    private val homeLabel = NavigationLabel.Home.label
    private val teamLabel = NavigationLabel.Team.label

    @Test
    fun initialScreenIsHomeScreenAndHomeSelected() {
        // when
        composeTestRule.setContent { AppNav() }

        // then
        composeTestRule.onNodeWithTag("pokemon_home_column").assertExists()
        composeTestRule.onNodeWithTag("nav_item_Pokédex").assertIsSelected()
        composeTestRule.onNodeWithText(randomLabel).assertIsNotSelected()
        composeTestRule.onNodeWithText(teamLabel).assertIsNotSelected()
    }

    @Test
    fun bottomNavigationBarExistsWithCorrectItems() {
        // when
        composeTestRule.setContent { AppNav() }

        // then
        composeTestRule.onNodeWithTag("bottom_navigation").assertExists()
        composeTestRule.onNodeWithTag("nav_item_Pokédex").assertExists()
        composeTestRule.onNodeWithText(randomLabel).assertExists()
        composeTestRule.onNodeWithText(teamLabel).assertExists()
    }

    @Test
    fun clickRandomNavItemNavigatesToRandomScreenAndSelectsRandom() {
        // when
        composeTestRule.setContent { AppNav() }
        composeTestRule.onNodeWithText(randomLabel).performClick()

        // then
        composeTestRule.onNodeWithTag("random_screen_content").assertExists("Random screen content not found after click.")
        composeTestRule.onNodeWithText(randomLabel).assertIsSelected()
        composeTestRule.onNodeWithText(homeLabel).assertIsNotSelected()
        composeTestRule.onNodeWithText(teamLabel).assertIsNotSelected()
    }

    @Test
    fun clickTeamNavItemNavigatesToTeamScreenAndSelectsTeam() {
        // when
        composeTestRule.setContent { AppNav() }
        composeTestRule.onNodeWithText(teamLabel).performClick()

        // then
        composeTestRule.onNodeWithTag("teamHeader").assertExists("Team screen content not found after click.")
        composeTestRule.onNodeWithText(teamLabel).assertIsSelected()
        composeTestRule.onNodeWithText(homeLabel).assertIsNotSelected()
        composeTestRule.onNodeWithText(randomLabel).assertIsNotSelected()
    }

    @Test
    fun clickHomeNavItem_fromOtherScreen_navigatesToHomeScreen_andSelectsHome() {
        // when
        composeTestRule.setContent { AppNav() }
        composeTestRule.onNodeWithText(randomLabel).performClick()
        composeTestRule.waitForIdle()

        // then
        composeTestRule.onNodeWithTag("random_screen_content").assertExists()
        composeTestRule.onNodeWithText(randomLabel).assertIsSelected()


        // when
        composeTestRule.onNodeWithTag("nav_item_Pokédex").performClick()
        composeTestRule.waitForIdle()

        // then
        composeTestRule.onNodeWithTag("pokemon_home_column").assertExists("Home screen content not found after navigating back.")
        composeTestRule.onNodeWithTag("nav_item_Pokédex").assertIsSelected()
        composeTestRule.onNodeWithText(randomLabel).assertIsNotSelected()
        composeTestRule.onNodeWithText(teamLabel).assertIsNotSelected()
    }
}