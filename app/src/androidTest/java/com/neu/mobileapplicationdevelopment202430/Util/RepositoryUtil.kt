package com.neu.mobileapplicationdevelopment202430.Util

import androidx.paging.PagingData
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import com.neu.mobileapplicationdevelopment202430.product.PokemonResult
import com.neu.mobileapplicationdevelopment202430.room.IPokemonRepository
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakePokemonRepository : IPokemonRepository {
    private var nextRandomResult: PokemonResult<TeamPokemonEntity> = PokemonResult.Idle
    private var pagedDataFlow: Flow<PagingData<PokemonItem>> = flowOf(PagingData.empty())

    fun setNextRandomResult(result: PokemonResult<TeamPokemonEntity>) {
        nextRandomResult = result
    }

    fun setPagedResult(flow: Flow<PagingData<PokemonItem>>) {
        pagedDataFlow = flow
    }

    override suspend fun getRandomPokemon(): PokemonResult<TeamPokemonEntity> {
        delay(1)
        return nextRandomResult
    }

    override fun getPagedPokemon(): Flow<PagingData<PokemonItem>> {
        return pagedDataFlow
    }
}