package com.neu.mobileapplicationdevelopment202430.product

import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonRandomResponse
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonResponse
import com.neu.mobileapplicationdevelopment202430.room.PokemonEntity
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {
    @GET("/getRandomPokemon")
    suspend fun getRandomPokemon(): Response<PokemonRandomResponse>

    @GET("/getPokemon")
    suspend fun getPokemonByPage(@Query("page") page: Int): Response<PokemonResponse>
}

sealed class PokemonResult<out T> {
    data object Idle : PokemonResult<Nothing>()
    data object Loading : PokemonResult<Nothing>()
    data class Success<T>(val pokemon: T) : PokemonResult<T>()
    data class Error(val message: String) : PokemonResult<Nothing>()
}

object RetrofitClient {
    private const val BASE_URL = "https://sidgaikwad.pythonanywhere.com/"

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val productService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}

