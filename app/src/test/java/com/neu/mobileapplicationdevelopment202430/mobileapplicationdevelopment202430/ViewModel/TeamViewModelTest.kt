package com.neu.mobileapplicationdevelopment202430.ViewModel

import com.neu.mobileapplicationdevelopment202430.Fake.FakeTeamPokemonDao
import com.neu.mobileapplicationdevelopment202430.pokemon.TeamViewModel
import com.neu.mobileapplicationdevelopment202430.pokemon.TeamViewModelFactory
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TeamViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: TeamViewModel
    private lateinit var fakeDao: FakeTeamPokemonDao

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeDao = FakeTeamPokemonDao()
    }


    @Test
    fun `removePokemon calls dao with correct id`() = runTest(testDispatcher) {
        val fakeTeam = listOf(
            TeamPokemonEntity(25, "Pikachu", "Electric", null, "Static", "Mouse", "Desc", "sprite")
        )
        fakeDao.setInitialTeam(fakeTeam)
        viewModel = TeamViewModel(fakeDao)

        viewModel.removePokemon(25)
        advanceUntilIdle()

        val updatedTeam = fakeDao.getCurrentTeamSnapshot()
        assertEquals(0, updatedTeam.size)
    }

    @Test
    fun `TeamViewModelFactory creates TeamViewModel`() {
        val factory = TeamViewModelFactory(fakeDao)
        val viewModel = factory.create(TeamViewModel::class.java)

        assertEquals(true, viewModel is TeamViewModel)
    }

}