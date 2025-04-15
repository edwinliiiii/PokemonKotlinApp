package com.neu.mobileapplicationdevelopment202430.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.neu.mobileapplicationdevelopment202430.Fake.FakePokemonRepository
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonListViewModel
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonViewModelFactory
import com.neu.mobileapplicationdevelopment202430.product.PokemonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PokemonListViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeRepository: FakePokemonRepository
    private lateinit var viewModel: PokemonListViewModel

    private val samplePokemon1 = PokemonItem(
        id = 1,
        name = "Bulbasaur",
        type1 = "Grass",
        type2 = "Poison",
        abilities = "Overgrow, Chlorophyll",
        species = "Seed Pokémon",
        description = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun’s rays, the seed grows progressively larger.",
        sprite = "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/images/pokedex/sprites/001.png"
    )
    private val samplePokemon2 = PokemonItem(
        id = 4,
        name = "Charmander",
        type1 = "Fire",
        type2 = null,
        abilities = "Blaze, Solar Power",
        species = "Lizard Pokémon",
        description = "The flame that burns at the tip of its tail is an indication of its emotions. The flame wavers when Charmander is enjoying itself. If the Pokémon becomes enraged, the flame burns fiercely.",
        sprite = "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/images/pokedex/sprites/004.png"
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakePokemonRepository()
        viewModel = PokemonListViewModel(fakeRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenPagedPokemonIsRetrievedFromServer(): Unit = runTest(testDispatcher) {
        val pokemonItems = listOf(samplePokemon1,samplePokemon2)
        val initialState = viewModel.pokemon.value
        assertTrue(initialState is PokemonResult.Loading)
        val paging = PagingData.from(pokemonItems)
        fakeRepository.setPagedResult(flowOf(paging))
        val result = viewModel.pagedPokemon.first()
        assertTrue(result != PagingData.empty<PokemonItem>())
    }

    @Test
    fun whenFactoryIsFirstCreated(): Unit = runTest(testDispatcher) {
        val factory = PokemonViewModelFactory(fakeRepository)
        val viewModel = factory.create(PokemonListViewModel::class.java)
        assertTrue(viewModel is PokemonListViewModel)
    }

    @Test
    fun whenCreatingAPokemonListViewModel(): Unit = runTest(testDispatcher) {
        val factory = PokemonViewModelFactory(fakeRepository)
        val viewModel = factory.create(PokemonListViewModel::class.java)
        assertEquals(fakeRepository, viewModel.repository)
    }
}

