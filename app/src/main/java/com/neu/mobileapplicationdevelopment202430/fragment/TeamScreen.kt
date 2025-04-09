package com.neu.mobileapplicationdevelopment202430.fragment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.neu.mobileapplicationdevelopment202430.R
import com.neu.mobileapplicationdevelopment202430.pokemon.TeamViewModel
import com.neu.mobileapplicationdevelopment202430.pokemon.TeamViewModelFactory
import com.neu.mobileapplicationdevelopment202430.room.PokemonDatabase
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity
import com.neu.mobileapplicationdevelopment202430.team.TeamPlaceholderCard
import com.neu.mobileapplicationdevelopment202430.team.TeamPokemonCard

@Composable
fun TeamScreen(navController: NavController) {
    val context = LocalContext.current
    val db = PokemonDatabase.getDatabase(context)
    val dao = db.teamPokemonDao()
    val factory = TeamViewModelFactory(dao)
    val viewModel: TeamViewModel = viewModel(factory = factory)
    val team by viewModel.team.collectAsState(initial = emptyList())

    // TODO: Remove
    LaunchedEffect(Unit) {
        val dummyTeam = listOf(
            TeamPokemonEntity(
                id = 1,
                name = "Bulbasaur",
                type1 = "Grass",
                type2 = "Poison",
                abilities = "Overgrow",
                species = "Seed Pokémon",
                description = "A strange seed was planted on its back at birth.",
                sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
            ),
            TeamPokemonEntity(
                id = 4,
                name = "Charmander",
                type1 = "Fire",
                type2 = null,
                abilities = "Blaze",
                species = "Lizard Pokémon",
                description = "Obviously prefers hot places.",
                sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png"
            )
        )

        dummyTeam.forEach {
            viewModel.addPokemonToTeam(it)
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF2B5876)
        ) {
            Text(
                text = stringResource(id = R.string.team),
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        LazyColumn {
            items(team) { pokemon ->
                TeamPokemonCard(
                    pokemon = pokemon,
                    onRemove = { viewModel.removePokemon(pokemon.id) }
                )
            }

            val missingCount = 6 - team.size
            items(missingCount) {
                TeamPlaceholderCard()
            }
        }


        Button(
            onClick = { navController.navigate("coverage") },
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp)
        ) {
            Text("Check Coverage")
        }
    }
}
