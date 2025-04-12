package com.neu.mobileapplicationdevelopment202430.room

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import com.neu.mobileapplicationdevelopment202430.product.PokemonApiService
import kotlinx.coroutines.flow.Flow

class PokemonRepository(val pokemonDao: PokemonDao, val pokemonApiService: PokemonApiService) {
    fun getPagedPokemon(): Flow<PagingData<PokemonItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false,
                maxSize = 75
            ),
            pagingSourceFactory = {
                PokemonPaging(pokemonApiService, pokemonDao)
            }
        ).flow
    }
}