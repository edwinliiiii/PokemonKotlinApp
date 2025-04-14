package com.neu.mobileapplicationdevelopment202430.Fake

import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonRandomResponse
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonResponse
import com.neu.mobileapplicationdevelopment202430.product.PokemonApiService
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakePokemonApiService : PokemonApiService {
    private var nextRandomResponse: Response<PokemonRandomResponse>? = null
    private var randomException: Exception? = null

    fun setNextRandomResponse(response: Response<PokemonRandomResponse>) {
        nextRandomResponse = response
        randomException = null
    }

    fun setNextRandomException(exception: Exception) {
        randomException = exception
        nextRandomResponse = null
    }

    override suspend fun getRandomPokemon(): Response<PokemonRandomResponse> {
        delay(1)
        randomException?.let {
            throw it
        }
        return nextRandomResponse ?: createDefaultErrorResponse()
    }

    private var nextPageResponse: Response<PokemonResponse>? = null
    private var pageException: Exception? = null

    fun setNextPageResponse(response: Response<PokemonResponse>) {
        nextPageResponse = response
        pageException = null
    }

    fun setNextPageException(exception: Exception) {
        pageException = exception
        nextPageResponse = null
    }

    override suspend fun getPokemonByPage(page: Int): Response<PokemonResponse> {
        delay(1)
        pageException?.let {
            throw it
        }
        return nextPageResponse ?: createDefaultPageErrorResponse("Paged Pokemon endpoint not assigned for page $page")
    }

    // res helpers
    private fun <T> createDefaultErrorResponse(): Response<T> {
        return Response.error(
            500,
            "Error: Random Pokemon endpoint not assigned".toResponseBody("application/json".toMediaTypeOrNull())
        )
    }

    private fun createDefaultPageErrorResponse(message: String): Response<PokemonResponse> {
        return Response.error(
            500,
            "Error: $message".toResponseBody("application/json".toMediaTypeOrNull())
        )
    }
}