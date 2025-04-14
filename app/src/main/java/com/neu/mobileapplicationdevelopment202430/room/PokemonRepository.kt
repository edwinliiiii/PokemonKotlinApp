package com.neu.mobileapplicationdevelopment202430.room

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import com.neu.mobileapplicationdevelopment202430.product.PokemonApiService
import com.neu.mobileapplicationdevelopment202430.product.PokemonResult
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

interface IPokemonRepository {
    fun getPagedPokemon(): Flow<PagingData<PokemonItem>>
    suspend fun getRandomPokemon(): PokemonResult<TeamPokemonEntity>
}

class PokemonRepository(val pokemonDao: PokemonDao, val pokemonApiService: PokemonApiService)
    : IPokemonRepository {
    override fun getPagedPokemon(): Flow<PagingData<PokemonItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false,
                prefetchDistance = 1,
            ),
            pagingSourceFactory = {
                PokemonPaging(pokemonApiService, pokemonDao)
            }
        ).flow
    }

    override suspend fun getRandomPokemon(): PokemonResult<TeamPokemonEntity> {
        return try {
            val response = pokemonApiService.getRandomPokemon()
            val body = response.body()
            System.out.println(response.body())
            if (response.isSuccessful && body != null) { // on success
                PokemonResult.Success(body.pokemon)
            } else { // display err details
                PokemonResult.Error("Random Gen Failed: ${response.code()}, ${response.message()}")
            }
        } catch (e: IOException) {
            Log.d("PokemonRepository", "IO error, failed to fetch random pokemon")
            PokemonResult.Error("Network error: please check your connection.")
        } catch (e: HttpException) {
            Log.d("PokemonRepository", "HTTP error, failed to fetch random pokemon")
            PokemonResult.Error("Random Gen Failed: ${e.code()}, ${e.message()}")
        } catch (e: Exception) {
            Log.d("PokemonRepository", "Unknown error, failed to fetch random pokemon")
            PokemonResult.Error("Unexpected error occurred.")
        }
    }
}