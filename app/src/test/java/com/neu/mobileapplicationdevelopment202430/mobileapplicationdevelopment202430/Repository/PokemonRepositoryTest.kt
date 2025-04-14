package com.neu.mobileapplicationdevelopment202430.Repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.neu.mobileapplicationdevelopment202430.Fake.FakePokemonApiService
import com.neu.mobileapplicationdevelopment202430.Fake.FakePokemonDao
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonRandomResponse
import com.neu.mobileapplicationdevelopment202430.product.PokemonResult
import com.neu.mobileapplicationdevelopment202430.room.PokemonRepository
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class PokemonRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var fakePokemonDao: FakePokemonDao
    private lateinit var fakePokemonApiService: FakePokemonApiService
    private lateinit var repository: PokemonRepository

    private val samplePokemon1 = TeamPokemonEntity(
        id = 1,
        name = "Bulbasaur",
        type1 = "Grass",
        type2 = "Poison",
        abilities = "Overgrow, Chlorophyll",
        species = "Seed Pokémon",
        description = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun’s rays, the seed grows progressively larger.",
        sprite = "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/images/pokedex/sprites/001.png"
    )

    private val sampleApiResponse = PokemonRandomResponse(pokemon = samplePokemon1)

    @Before
    fun setUp() {
        fakePokemonDao = FakePokemonDao()
        fakePokemonApiService = FakePokemonApiService()
        repository = PokemonRepository(fakePokemonDao, fakePokemonApiService)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getRandomPokemonWhenApiServiceReturnsSuccessReturnsSuccessResult(): Unit = runTest {
        // given
        val successResponse: Response<PokemonRandomResponse> = Response.success(sampleApiResponse)
        fakePokemonApiService.setNextRandomResponse(successResponse)

        // when
        val result = repository.getRandomPokemon()

        // then
        assertThat(result, instanceOf(PokemonResult.Success::class.java))
        assertEquals(samplePokemon1, (result as PokemonResult.Success).pokemon)
    }

    @Test
    fun getRandomPokemonWhenApiServiceReturnsAPIErrorReturnsErrorResult() = runTest {
        // given
        val errorBody = "Error: Not Found"
            .toResponseBody("application/json".toMediaTypeOrNull())
        val errorResponse: Response<PokemonRandomResponse> = Response.error(404, errorBody)
        fakePokemonApiService.setNextRandomResponse(errorResponse)

        // when
        val result = repository.getRandomPokemon()

        // then
        assertThat(result, instanceOf(PokemonResult.Error::class.java))
        val errorMessage = (result as PokemonResult.Error).message
        assertThat(errorMessage, containsString("404"))
        assertThat(errorMessage, containsString("Random Gen Failed:"))
    }

    @Test
    fun getRandomPokemonWhenApiServiceReturnsSuccessWithNullBodyReturnsErrorResult() = runTest {
        // given
        val nullBodyResponse: Response<PokemonRandomResponse> = Response.success(null)
        fakePokemonApiService.setNextRandomResponse(nullBodyResponse)

        // when
        val result = repository.getRandomPokemon()

        // then
        assertThat(result, instanceOf(PokemonResult.Error::class.java))
        val errorMessage = (result as PokemonResult.Error).message
        assertThat(errorMessage, containsString("Random Gen Failed:"))
        assertThat(errorMessage, containsString("200")) // res should still be success
    }

    @Test
    fun getRandomPokemonWhenApiServiceThrowsIOExceptionReturnsNetworkErrorResult() = runTest {
        // given
        val ioException = IOException("Network connection lost")
        fakePokemonApiService.setNextRandomException(ioException)

        // when
        val result = repository.getRandomPokemon()

        // then
        assertThat(result, instanceOf(PokemonResult.Error::class.java))
        assertEquals("Network error: please check your connection.", (result as PokemonResult.Error).message)
    }

    @Test
    fun getRandomPokemonWhenApiServiceThrowsHttpExceptionReturnsHTTPErrorResult() = runTest {
        // given
        val errorBody = "Error: Server Error"
            .toResponseBody("application/json".toMediaTypeOrNull())
        val errorResponse: Response<PokemonRandomResponse> = Response.error(500, errorBody)
        val httpException = HttpException(errorResponse)
        fakePokemonApiService.setNextRandomException(httpException)

        // when
        val result = repository.getRandomPokemon()

        // then
        assertThat(result, instanceOf(PokemonResult.Error::class.java))
        val errorMessage = (result as PokemonResult.Error).message
        assertThat(errorMessage, containsString("Random Gen Failed:"))
        assertThat(errorMessage, containsString("500"))
    }

    @Test
    fun getPagedPokemonReturnsNonNullFlow() {
        // when
        val resultFlow: Flow<PagingData<PokemonItem>> = repository.getPagedPokemon()

        // then
        assertNotNull("The returned flow should not be null", resultFlow)
    }
}