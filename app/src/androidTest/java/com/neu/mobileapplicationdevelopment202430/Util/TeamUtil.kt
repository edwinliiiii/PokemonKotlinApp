package com.neu.mobileapplicationdevelopment202430.Util

import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonDao
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeTeamPokemonDao : TeamPokemonDao {
    private val teamFlow = MutableStateFlow<List<TeamPokemonEntity>>(emptyList())

    fun setInitialTeam(initialTeam: List<TeamPokemonEntity>) {
        teamFlow.value = initialTeam.sortedBy { it.name }
    }

    fun getCurrentTeamSnapshot(): List<TeamPokemonEntity> = teamFlow.value

    override suspend fun addToTeam(pokemon: TeamPokemonEntity) {
        val currentTeam = teamFlow.value
        if (currentTeam.none { it.id == pokemon.id }) {
            val updatedTeam = (currentTeam + pokemon).sortedBy { it.name }
            teamFlow.value = updatedTeam
        }
    }

    override fun getTeam(): Flow<List<TeamPokemonEntity>> {
        return teamFlow.asStateFlow()
    }

    override suspend fun removeFromTeam(pokemonId: Int) {
        val currentTeam = teamFlow.value
        val updatedTeam = currentTeam.filterNot { it.id == pokemonId }
        if (updatedTeam.size != currentTeam.size) {
            teamFlow.value = updatedTeam
        }
    }

    override suspend fun getTeamSize(): Int {
        return teamFlow.value.size
    }
}