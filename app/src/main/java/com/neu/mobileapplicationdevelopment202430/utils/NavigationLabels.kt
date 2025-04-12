package com.neu.mobileapplicationdevelopment202430.utils

import androidx.annotation.DrawableRes
import com.neu.mobileapplicationdevelopment202430.R

sealed class NavigationLabel(
    val route: String,
    val label: String,
    @DrawableRes val icon: Int
) {
    object Random : NavigationLabel("random", "Random", R.drawable.question_mark)
    object Home : NavigationLabel("home", "Pokédex", R.drawable.pokedex)
    object Team : NavigationLabel("team", "Team", R.drawable.pokeball)
}

val NavigationBarTabs = listOf(
    NavigationLabel.Random, NavigationLabel.Home, NavigationLabel.Team
)