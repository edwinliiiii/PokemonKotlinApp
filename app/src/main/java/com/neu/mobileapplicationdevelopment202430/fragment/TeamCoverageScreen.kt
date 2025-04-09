package com.neu.mobileapplicationdevelopment202430.fragment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neu.mobileapplicationdevelopment202430.pokemon.TeamViewModel
import com.neu.mobileapplicationdevelopment202430.pokemon.TeamViewModelFactory
import com.neu.mobileapplicationdevelopment202430.room.PokemonDatabase
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity

@Composable
fun TeamCoverageScreen() {
    val context = LocalContext.current
    val db = PokemonDatabase.getDatabase(context)
    val dao = db.teamPokemonDao()
    val factory = TeamViewModelFactory(dao)
    val viewModel: TeamViewModel = viewModel(factory = factory)
    val team by viewModel.team.collectAsState(initial = emptyList())

    val allTypes = listOf(
        "Fire", "Water", "Grass", "Electric", "Ground", "Flying", "Poison",
        "Psychic", "Dark", "Steel", "Dragon", "Fairy", "Rock", "Bug",
        "Ghost", "Fighting", "Ice", "Normal"
    )

    val ownedTypes = team.flatMap { listOf(it.type1, it.type2 ?: "") }.filter { it.isNotBlank() }.toSet()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(allTypes) { type ->
            Text(
                text = "$type - ${if (ownedTypes.contains(type)) "✔" else "✘"}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}
