package com.neu.mobileapplicationdevelopment202430.room

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
            PokemonResult.Error("Network error: please check your connection.")
        } catch (e: HttpException) {
            PokemonResult.Error("Random Gen Failed: ${e.code()}, ${e.message()}")
        } catch (e: Exception) {
            PokemonResult.Error("Unexpected error occurred.")
        }
    }
}