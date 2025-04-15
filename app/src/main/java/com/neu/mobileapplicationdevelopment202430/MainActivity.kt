package com.neu.mobileapplicationdevelopment202430

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.neu.mobileapplicationdevelopment202430.fragment.HomeScreen
import com.neu.mobileapplicationdevelopment202430.fragment.PokemonInfoScreen
import com.neu.mobileapplicationdevelopment202430.fragment.RandomScreen
import com.neu.mobileapplicationdevelopment202430.fragment.TeamCoverageScreen
import com.neu.mobileapplicationdevelopment202430.fragment.TeamScreen
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import com.neu.mobileapplicationdevelopment202430.utils.NavigationBarTabs
import com.neu.mobileapplicationdevelopment202430.utils.NavigationLabel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNav()
        }
    }
}

@Composable
fun AppNav() {
    MaterialTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavigation(backgroundColor = Color.DarkGray, modifier = Modifier.testTag("bottom_navigation")) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    NavigationBarTabs.forEach { tab ->
                        BottomNavigationItem(
                            modifier = Modifier.testTag("nav_item_${tab.label}"),
                            icon = { Icon(painter = painterResource(tab.icon), contentDescription = tab.label, modifier = Modifier.size(96.dp), tint = Color.Unspecified ) },
                            label = { Text(tab.label, color = Color.White) },
                            selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true,
                            onClick = {
                                navController.navigate(tab.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = NavigationLabel.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(NavigationLabel.Random.route) {
                    RandomScreen()
                }
                composable(NavigationLabel.Home.route) {
                    HomeScreen(navController = navController)
                }
                composable(NavigationLabel.Team.route) {
                    TeamScreen(navController = navController)
                }
                composable("pokemonInfo") {
                    val pokemon =
                        navController.previousBackStackEntry?.savedStateHandle?.get<PokemonItem>(
                            "pokemon"
                        )
                    pokemon?.let {
                        PokemonInfoScreen(pokemon = it)
                    }
                }
                composable("coverage") {
                    TeamCoverageScreen()
                }
            }
        }
    }
}
