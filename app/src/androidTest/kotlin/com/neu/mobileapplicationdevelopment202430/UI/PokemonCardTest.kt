package com.neu.mobileapplicationdevelopment202430.UI

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonCard
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokemonCardTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    private val samplePokemon = PokemonItem(
        id = 1,
        name = "Bulbasaur",
        sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
        species = "Seed Pokémon",
        type1 = "Grass",
        type2 = "Poison",
        abilities = "Overgrow",
        description = "A strange seed was planted on its back at birth. The plant sprouts and grows with this Pokémon."
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                PokemonCard(samplePokemon)
            }
        }
    }

    @Test
    fun testPokemonInfoCardExists() {
        composeTestRule.onNode(hasTestTag("pokemon_card_1")).assertExists()
    }

    @Test
    fun testPokemonInfoColumnExists() {
        composeTestRule.onNode(hasTestTag("pokemon_card_column_1")).assertExists()
    }

    @Test
    fun testPokemonInfoImageContainer() {
        composeTestRule.onNode(hasTestTag("pokemon_card_image_container_1")).assertExists()
    }

    @Test
    fun testPokemonInfoImage() {
        composeTestRule.onNode(hasTestTag("pokemon_card_image_1")).assertExists()
    }

    @Test
    fun testPokemonInfoName() {
        composeTestRule.onNode(hasTestTag("pokemon_card_name_1")).assertExists()
        composeTestRule.onNode(hasTestTag("pokemon_card_name_1")).assertTextEquals("Bulbasaur")
    }

    @Test
    fun testPokemonInfoSpecies() {
        composeTestRule.onNode(hasTestTag("pokemon_card_species_1")).assertExists()
        composeTestRule.onNode(hasTestTag("pokemon_card_species_1")).assertTextEquals("Seed Pokémon")
    }
}