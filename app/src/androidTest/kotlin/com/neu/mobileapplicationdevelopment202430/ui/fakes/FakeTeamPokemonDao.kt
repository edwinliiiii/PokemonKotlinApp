package com.neu.mobileapplicationdevelopment202430.ui.fakes

import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonDao
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeTeamPokemonDao : TeamPokemonDao {
    private val fakeTeam = listOf(
        TeamPokemonEntity(
            id = 25,
            name = "Pikachu",
            type1 = "Electric",
            type2 = null,
            abilities = "Static",
            species = "Mouse Pokémon",
            description = "Electric mouse Pokémon.",
            sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"
        )
    )

    override fun getTeam(): Flow<List<TeamPokemonEntity>> = flowOf(fakeTeam)
    override suspend fun getTeamSize(): Int = fakeTeam.size
    override suspend fun addToTeam(pokemon: TeamPokemonEntity) {}
    override suspend fun removeFromTeam(id: Int) {}
}
