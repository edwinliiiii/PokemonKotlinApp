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
import androidx.compose.ui.platform.testTag
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

    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("teamHeader"),
            color = Color(0xFF2B5876)
        ) {
            Text(
                text = stringResource(id = R.string.team),
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .testTag("teamScreenTitle"),
                textAlign = TextAlign.Center
            )
        }

        LazyColumn (
            modifier = Modifier
                .weight(1f)
                .testTag("teamList")) {
            items(team) { pokemon ->
                TeamPokemonCard(
                    pokemon = pokemon,
                    onRemove = { viewModel.removePokemon(pokemon.id) },
                    modifier = Modifier.testTag("teamPokemonCard_${pokemon.id}")
                )
            }

            val missingCount = 6 - team.size
            items(missingCount) {
                TeamPlaceholderCard(
                    modifier = Modifier.testTag("placeholderCard")
                )
            }
        }


        Button(
            onClick = { navController.navigate("coverage") },
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp)
                .testTag("checkCoverageButton")
        ) {
            Text("Check Coverage")
        }
    }
}
