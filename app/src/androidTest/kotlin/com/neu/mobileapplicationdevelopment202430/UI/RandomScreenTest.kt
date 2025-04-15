package com.neu.mobileapplicationdevelopment202430.UI

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.neu.mobileapplicationdevelopment202430.fragment.RandomScreen
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RandomScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val samplePokemon1 = TeamPokemonEntity(
        id = 1,
        name = "Bulbasaur",
        type1 = "Grass",
        type2 = "Poison",
        abilities = "Overgrow, Chlorophyll",
        species = "Seed Pokémon",
        description = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun’s rays, the seed grows progressively larger.",
        sprite = "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/images/pokedex/sprites/001.png"
    )

    @Test
    fun displaysInitialStateCorrectly() {
        // when
        composeTestRule.setContent { RandomScreen() }

        // then
        composeTestRule.onNodeWithTag("random_screen_content").assertExists()
        composeTestRule.onNodeWithText("Click Generate!").assertExists()
        composeTestRule.onNodeWithText("???").assertExists()
        composeTestRule.onNodeWithText("Add to Team").assertIsNotEnabled()
    }

    @Test
    fun randomScreenButtonsAreClickable() {
        // when
        composeTestRule.setContent { RandomScreen() }

        // then
        composeTestRule.onNodeWithTag("generate_button").assertIsEnabled()
        composeTestRule.onNodeWithText("Add to Team").assertIsNotEnabled()

        // when
        composeTestRule.onNodeWithTag("generate_button").performClick()

        // then
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule
                    .onNodeWithText("Add to Team")
                    .assertIsEnabled()
                true
            } catch (e: AssertionError) {
                false
            }
        }

        // then 2
        composeTestRule.onNodeWithText("Add to Team").assertIsEnabled()
    }

    @Test
    fun randomScreenDisplaysSuccessStateCorrectly() {
        // when
        composeTestRule.setContent { RandomScreen() }
        composeTestRule.onNodeWithTag("generate_button").performClick()
        composeTestRule.waitForIdle()

        // then
        composeTestRule.onNodeWithTag("random_screen_content").assertExists()
        composeTestRule.onNodeWithText("Click Generate!").assertDoesNotExist()
    }
}