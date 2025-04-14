package com.neu.mobileapplicationdevelopment202430.random

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.neu.mobileapplicationdevelopment202430.product.PokemonResult
import com.neu.mobileapplicationdevelopment202430.room.IPokemonRepository
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonDao
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RandomViewModelFactory(
    private val repository: IPokemonRepository,
    private val teamDao: TeamPokemonDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RandomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RandomViewModel(repository, teamDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class RandomViewModel(
    private val repository: IPokemonRepository,
    private val teamDao: TeamPokemonDao
) : ViewModel() {
    private val _randomPokemonState = MutableStateFlow<PokemonResult<TeamPokemonEntity>>(PokemonResult.Idle)
    val randomPokemonState: StateFlow<PokemonResult<TeamPokemonEntity>> = _randomPokemonState.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun generateRandom() {
        viewModelScope.launch {
            _randomPokemonState.value = PokemonResult.Loading
            val result = repository.getRandomPokemon()
            _randomPokemonState.value = result
        }
    }

    fun addToTeam() {
        val currentState = _randomPokemonState.value
        if (currentState is PokemonResult.Success) {
            val currentPokemon = currentState.pokemon
            viewModelScope.launch {
                if (teamDao.getTeamSize() >= 6) {
                    _toastMessage.value = "Your team is already full (Max 6 Pokémon)."
                    return@launch
                }
                val team = teamDao.getTeam().first()
                if (team.any { it.id == currentPokemon.id }) {
                    _toastMessage.value = "${currentPokemon.name} is already in your team."
                    return@launch
                }
                try {
                    val teamEntity = TeamPokemonEntity(
                        id = currentPokemon.id,
                        name = currentPokemon.name,
                        species = currentPokemon.species,
                        type1 = currentPokemon.type1,
                        type2 = currentPokemon.type2,
                        abilities = currentPokemon.abilities,
                        description = currentPokemon.description,
                        sprite = currentPokemon.sprite
                    )
                    teamDao.addToTeam(teamEntity)
                    _toastMessage.value = "${currentPokemon.name} was added to your team!"
                } catch (e: Exception) {
                    _toastMessage.value = "Failed to add Pokémon to your team."
                    Log.d("RandomViewModel", "Failed to add Pokémon to team: ${e.message}")
                }
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
}