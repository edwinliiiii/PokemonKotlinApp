package com.neu.mobileapplicationdevelopment202430.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonDao
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TeamViewModelFactory(private val dao: TeamPokemonDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class TeamViewModel(private val dao: TeamPokemonDao): ViewModel() {
    val team: Flow<List<TeamPokemonEntity>> = dao.getTeam()

    fun removePokemon(id: Int) {
        viewModelScope.launch {
            dao.removeFromTeam(id)
        }
    }

    suspend fun teamHasRoom(): Boolean {
        return dao.getTeamSize() < 6
    }

    suspend fun isInTeam(pokemonId: Int): Boolean {
        return dao.getTeam().first().any { it.id == pokemonId }
    }

    suspend fun addPokemonToTeam(pokemon: TeamPokemonEntity) {
        dao.addToTeam(pokemon)
    }
}


