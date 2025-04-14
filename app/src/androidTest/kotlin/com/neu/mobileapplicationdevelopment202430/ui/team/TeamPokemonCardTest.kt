package com.neu.mobileapplicationdevelopment202430.ui.team

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity
import com.neu.mobileapplicationdevelopment202430.team.TeamPokemonCard
import com.neu.mobileapplicationdevelopment202430.team.TeamPlaceholderCard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@RunWith(AndroidJUnit4::class)
class TeamPokemonCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_teamPokemonCard_displays_correct_content() {
        val testPokemon = TeamPokemonEntity(
            id = 25,
            name = "Pikachu",
            type1 = "Electric",
            type2 = null,
            abilities = "Static",
            species = "Mouse Pokémon",
            description = "Electric mouse Pokémon.",
            sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"
        )

        composeTestRule.setContent {
            TeamPokemonCard(
                pokemon = testPokemon,
                onRemove = {},
                modifier = Modifier.testTag("teamPokemonCard_25")
            )
        }

        composeTestRule.onNodeWithTag("teamPokemonCard_25").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pokemonImage_25").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pokemonName_25").assertTextEquals("Pikachu")
        composeTestRule.onNodeWithTag("pokemonType1_25").assertTextEquals("Electric")
        composeTestRule.onNodeWithTag("pokemonType2_25").assertTextEquals("—")
    }

    @Test
    fun test_teamPlaceholderCard_displays_expected_ui() {
        composeTestRule.setContent {
            TeamPlaceholderCard(modifier = Modifier.testTag("placeholderCard"))
        }

        composeTestRule.onNodeWithTag("placeholderCard").assertIsDisplayed()
        composeTestRule.onNodeWithText("Empty Slot").assertIsDisplayed()
        composeTestRule.onAllNodesWithText("—").assertCountEquals(2)
        composeTestRule.onNodeWithContentDescription("Remove disabled").assertIsDisplayed()
    }
}
