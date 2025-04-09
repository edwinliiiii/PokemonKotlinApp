package com.neu.mobileapplicationdevelopment202430

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neu.mobileapplicationdevelopment202430.fragment.HomeScreen
import com.neu.mobileapplicationdevelopment202430.fragment.PokemonInfoScreen
import com.neu.mobileapplicationdevelopment202430.fragment.TeamScreen
import com.neu.mobileapplicationdevelopment202430.fragment.TeamCoverageScreen
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import com.neu.mobileapplicationdevelopment202430.pokemon.TeamViewModel
import com.neu.mobileapplicationdevelopment202430.pokemon.TeamViewModelFactory
import com.neu.mobileapplicationdevelopment202430.room.PokemonDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface (
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "team"
                    ) {
                        composable("home") {
                            HomeScreen(navController = navController)
                        }

                        composable("pokemonInfo") {
                            val pokemon =
                                navController.previousBackStackEntry?.savedStateHandle?.get<PokemonItem>(
                                    "pokemon"
                                )
                            pokemon?.let {
                                PokemonInfoScreen(pokemon = it, navController = navController)
                            }
                        }

                        composable("team") {
                            TeamScreen(navController = navController)
                        }

                        composable("coverage") {
                            TeamCoverageScreen()
                        }
                    }
                }
            }
        }
    }
}