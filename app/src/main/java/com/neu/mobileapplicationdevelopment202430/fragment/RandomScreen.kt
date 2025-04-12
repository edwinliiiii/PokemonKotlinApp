package com.neu.mobileapplicationdevelopment202430.fragment

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neu.mobileapplicationdevelopment202430.product.PokemonResult
import com.neu.mobileapplicationdevelopment202430.product.RetrofitClient
import com.neu.mobileapplicationdevelopment202430.random.RandomViewModel
import com.neu.mobileapplicationdevelopment202430.random.RandomViewModelFactory
import com.neu.mobileapplicationdevelopment202430.room.PokemonDatabase
import com.neu.mobileapplicationdevelopment202430.room.PokemonRepository
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RandomScreen() {
    // needed for RandomViewModel
    val context = LocalContext.current
    val database = PokemonDatabase.getDatabase(context)
    val teamDao = database.teamPokemonDao()
    val pokemonDao = database.pokemonDao()
    val apiService = RetrofitClient.productService

    val repository = PokemonRepository(pokemonDao, apiService)
    val viewModel: RandomViewModel = viewModel(factory = RandomViewModelFactory(repository, teamDao))

    val randomPokemonState by viewModel.randomPokemonState.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToastMessage()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF2B5876),
        ) {
            Text(
                text = "Random Generator",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (val state = randomPokemonState) {
                    is PokemonResult.Idle -> {
                        Box(modifier = Modifier.height(150.dp).width(150.dp)) {}
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Click Generate!", fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("???", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                    is PokemonResult.Loading -> {
                        Box(modifier = Modifier.height(150.dp).width(150.dp)) {}
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("???")
                    }
                    is PokemonResult.Error -> {
                        Box(modifier = Modifier.height(150.dp).width(150.dp)) { /* do nothing */ }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.message,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("???", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                    is PokemonResult.Success -> {
                        val pokemon = state.pokemon
                        GlideImage(
                            model = pokemon.sprite,
                            contentDescription = "${pokemon.name} sprite",
                            modifier = Modifier.size(150.dp),
                            alignment = Alignment.Center,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = pokemon.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = { viewModel.generateRandom() }) {
                    Text("Generate")
                }

                Button(
                    onClick = { viewModel.addToTeam() },
                    enabled = randomPokemonState is PokemonResult.Success
                ) {
                    Text("Add to Team")
                }
            }
        }
    }
}