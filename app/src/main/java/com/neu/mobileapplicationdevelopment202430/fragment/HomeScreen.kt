package com.neu.mobileapplicationdevelopment202430.fragment

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.gson.Gson
import com.neu.mobileapplicationdevelopment202430.R
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonCard
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonListViewModel
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonViewModelFactory
import com.neu.mobileapplicationdevelopment202430.product.RetrofitClient
import com.neu.mobileapplicationdevelopment202430.room.PokemonDatabase
import com.neu.mobileapplicationdevelopment202430.room.PokemonRepository

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val database = PokemonDatabase.getDatabase(context)
    val repository = PokemonRepository(database.pokemonDao(), RetrofitClient.productService)
    val viewModel: PokemonListViewModel = viewModel(factory = PokemonViewModelFactory(repository))
    val pagedPokemon = viewModel.pagedPokemon.collectAsLazyPagingItems()

    Column (
        modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF2B5876)
        ) {
            Text(
                text = stringResource(id = R.string.pokedex),
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(count = pagedPokemon.itemCount) { index ->
                pagedPokemon[index]?.let { pokemon ->
                    Card (
                        modifier = Modifier
                            .padding(4.dp)
                            .background(Color.White)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(32.dp),
                        onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("pokemon", pokemon)
                            navController.navigate("pokemonInfo")
                        }
                    ) {
                        PokemonCard(pokemon = pokemon)
                    }
                }

            }

            when (pagedPokemon.loadState.refresh) {
                is LoadState.Loading ->
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                is LoadState.Error ->
                    Toast.makeText(
                        context,
                        "Internal Service Error",
                        Toast.LENGTH_SHORT
                    ).show()

                is LoadState.NotLoading -> {}
            }
        }
    }

}