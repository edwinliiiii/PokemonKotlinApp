package com.neu.mobileapplicationdevelopment202430.room

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import com.neu.mobileapplicationdevelopment202430.product.PokemonApiService
import kotlinx.coroutines.flow.first
import java.io.IOException

class PokemonPaging(val pokemonService: PokemonApiService, val pokemonDao: PokemonDao): PagingSource<Int, PokemonItem>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonItem>): Int? {
        return state.anchorPosition?.let {
                anchorPosistion ->
            val anchorPage = state.closestPageToPosition(anchorPosistion)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonItem> {
        val page = params.key ?: 1
        try {
            val res = pokemonService.getPokemonByPage(page)
            Log.v("ProductPaging", "API response: ${res}")
            if (res.isSuccessful) {
                val pokemonResponse = res.body()
                if (pokemonResponse != null) {
                    val pageNumber = pokemonResponse.page
                    val pokemons = pokemonResponse.pokemon.map {
                        pokemon ->
                            pokemon.copy(
                                page = pageNumber,
                            )
                    }
                    val pokemonEntities = pokemons.map { it.toPokemonEntity() }
                    pokemonDao.deleteByPage(page)
                    pokemonDao.insertAll(pokemonEntities)
                    return LoadResult.Page(
                        data = pokemons,
                        prevKey = if (page > 1) page - 1 else null,
                        nextKey = if (page < 6) page + 1 else null
                    )
                }
                else {
                    return loadFromDatabase(page)
                }
            }
            else {
                return loadFromDatabase(page)
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    suspend fun loadFromDatabase(page: Int): LoadResult<Int, PokemonItem> {
        try {
            val products = pokemonDao.getPokemonByPage(page).first()
            if (!products.isNullOrEmpty()) {
                return LoadResult.Page(
                    data = products.map { it.toPokemonItem() },
                    prevKey = if (page > 1) page - 1 else null,
                    nextKey = if (page < 6) page + 1 else null
                )
            }
            else {
                return LoadResult.Error(throw IOException())
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }


}