package com.neu.mobileapplicationdevelopment202430

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.google.gson.Gson
import com.neu.mobileapplicationdevelopment202430.fragment.HomeScreen
import com.neu.mobileapplicationdevelopment202430.fragment.PokemonInfoScreen
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import java.util.concurrent.TimeUnit

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
                        startDestination = "home"
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
                    }

                }
            }
        }
    }
}