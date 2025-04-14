package com.neu.mobileapplicationdevelopment202430.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.neu.mobileapplicationdevelopment202430.Util.FakePokemonRepository
import com.neu.mobileapplicationdevelopment202430.Util.FakeTeamPokemonDao
import com.neu.mobileapplicationdevelopment202430.product.PokemonResult
import com.neu.mobileapplicationdevelopment202430.random.RandomViewModel
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RandomViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

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
    private val samplePokemon2 = TeamPokemonEntity(
        id = 4,
        name = "Charmander",
        type1 = "Fire",
        type2 = null,
        abilities = "Blaze, Solar Power",
        species = "Lizard Pokémon",
        description = "The flame that burns at the tip of its tail is an indication of its emotions. The flame wavers when Charmander is enjoying itself. If the Pokémon becomes enraged, the flame burns fiercely.",
        sprite = "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/images/pokedex/sprites/004.png"
    )

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeRepository: FakePokemonRepository
    private lateinit var fakeTeamDao: FakeTeamPokemonDao
    private lateinit var viewModel: RandomViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakePokemonRepository()
        fakeTeamDao = FakeTeamPokemonDao()
        viewModel = RandomViewModel(fakeRepository, fakeTeamDao)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenGenerateRandomAndRepositorySuccessThenStateIsSuccess(): Unit = runTest(testDispatcher) {
        // given
        val successResult = PokemonResult.Success(samplePokemon1)
        fakeRepository.setNextRandomResult(successResult)

        // when
        viewModel.generateRandom()
        advanceUntilIdle()

        // then
        assertEquals(successResult, viewModel.randomPokemonState.value)
    }

    @Test
    fun whenGenerateRandomAndRepositoryErrorThenStateIsError() = runTest(testDispatcher) {
        // given
        val errorResult = PokemonResult.Error("Network Failure")
        fakeRepository.setNextRandomResult(errorResult)

        // when
        viewModel.generateRandom()
        advanceUntilIdle()

        // then
        assertEquals(errorResult, viewModel.randomPokemonState.value)
    }

    @Test
    fun whenAddToTeamAndStateIsSuccessAndTeamHasRoomThenPokemonAddedAndSuccessMessageShown() = runTest(testDispatcher) {
        // given
        fakeRepository.setNextRandomResult(PokemonResult.Success(samplePokemon1))
        viewModel.generateRandom()
        advanceUntilIdle()
        fakeTeamDao.setInitialTeam(listOf(samplePokemon2))

        // when
        viewModel.addToTeam()
        advanceUntilIdle()

        // then
        val currentTeam = fakeTeamDao.getCurrentTeamSnapshot()
        assertTrue("Team should contain added Pokemon", currentTeam.any { it.id == samplePokemon1.id })
        assertEquals("Team size should be 2", 2, currentTeam.size)
        assertTrue(
            "Toast message should indicate success",
            viewModel.toastMessage.value?.contains("${samplePokemon1.name} was added") ?: false
        )
    }

    @Test
    fun whenAddToTeamAndStateIsSuccessAndTeamIsFullThenTeamFullMessageShown() = runTest(testDispatcher) {
        // given
        fakeRepository.setNextRandomResult(PokemonResult.Success(samplePokemon1))
        viewModel.generateRandom()
        advanceUntilIdle()

        val fullTeam = List(6) { i -> TeamPokemonEntity(i, "Name$i", "T1", null, "Abi", "Spec", "Desc", "Spri") }
        fakeTeamDao.setInitialTeam(fullTeam)
        val initialTeamSize = fakeTeamDao.getCurrentTeamSnapshot().size

        // when
        viewModel.addToTeam()
        advanceUntilIdle()

        // then
        assertEquals("Team size should not change", initialTeamSize, fakeTeamDao.getCurrentTeamSnapshot().size)
        assertTrue(
            "Toast message should indicate team is full",
            viewModel.toastMessage.value?.contains("team is already full") ?: false
        )
    }

    @Test
    fun whenAddToTeamAndStateIsSuccessAndPokemonExistsThenAlreadyInTeamMessageShown() = runTest(testDispatcher) {
        // given
        fakeRepository.setNextRandomResult(PokemonResult.Success(samplePokemon1))
        viewModel.generateRandom()
        advanceUntilIdle()

        fakeTeamDao.setInitialTeam(listOf(samplePokemon1, samplePokemon2))
        val initialTeamSize = fakeTeamDao.getCurrentTeamSnapshot().size

        // when
        viewModel.addToTeam()
        advanceUntilIdle()

        // then
        assertEquals("Team size should not change", initialTeamSize, fakeTeamDao.getCurrentTeamSnapshot().size)
        assertTrue(
            "Toast message should indicate pokemon exists",
            viewModel.toastMessage.value?.contains("${samplePokemon1.name} is already in your team") ?: false
        )
    }

    @Test
    fun whenClearToastMessageThenToastMessageIsNull() = runTest(testDispatcher) {
        // given
        fakeRepository.setNextRandomResult(PokemonResult.Success(samplePokemon1))
        viewModel.generateRandom()
        advanceUntilIdle()

        val fullTeam = List(6) { i -> TeamPokemonEntity(i, "Name$i", "T1", null, "Abi", "Spec", "Desc", "Spri") }
        fakeTeamDao.setInitialTeam(fullTeam)
        viewModel.addToTeam()
        advanceUntilIdle()
        assertNotNull("Toast message should be set initially", viewModel.toastMessage.value)

        // when
        viewModel.clearToastMessage()

        // then
        assertNull("Toast message should be null after clearing", viewModel.toastMessage.value)
    }
}