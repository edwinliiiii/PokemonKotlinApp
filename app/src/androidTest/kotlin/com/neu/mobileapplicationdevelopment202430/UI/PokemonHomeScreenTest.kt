package com.neu.mobileapplicationdevelopment202430.UI

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.neu.mobileapplicationdevelopment202430.fragment.HomeScreen
import com.neu.mobileapplicationdevelopment202430.fragment.PokemonInfoScreen
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class PokemonHomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: NavController

    @Composable
    fun TestNavHost() {
        navController = rememberNavController()

        NavHost(
            navController = navController as NavHostController,
            startDestination = "home"
        ) {
            composable("home") {
                HomeScreen(navController = navController)
            }
            composable("pokemonInfo") {
                val pokemon = navController.previousBackStackEntry?.savedStateHandle?.get<PokemonItem>("pokemon")
                if (pokemon != null) {
                    PokemonInfoScreen(pokemon = pokemon)
                }
            }
        }
    }

    @Test
    fun testHomeScreenHeader() {
        composeTestRule.setContent { TestNavHost() }
        composeTestRule.onNodeWithTag("pokemon_home_column").assertExists()
    }

    @Test
    fun testHomeScreenSurface() {
        composeTestRule.setContent { TestNavHost() }
        composeTestRule.onNodeWithTag("pokemon_home_surface").assertExists()
    }

    @Test
    fun testHomeScreenTitle() {
        composeTestRule.setContent { TestNavHost() }
        composeTestRule.onNodeWithTag("pokemon_home_pokedex").assertExists()
        composeTestRule.onNode(hasTestTag("pokemon_home_pokedex")).assertTextEquals("Pokédex")
    }

    @Test
    fun testHomeScreenBox() {
        composeTestRule.setContent { TestNavHost() }
        composeTestRule.onNodeWithTag("pokemon_home_box").assertExists()
    }

    @Test
    fun testHomeScreenPokemon() {
        composeTestRule.setContent { TestNavHost() }
        composeTestRule.waitUntil(10000) {
            composeTestRule
                .onAllNodesWithTag("pokemon_home_box")
                .fetchSemanticsNodes().isEmpty()
        }

        composeTestRule.onNode(hasTestTag("pokemon_home_pokemon_1")).assertExists()
        composeTestRule.onNode(hasTestTag("pokemon_home_pokemon_2")).assertExists()
        composeTestRule.onNodeWithText("Bulbasaur").performClick()
        assertEquals("pokemonInfo", navController.currentDestination?.route)
    }

}