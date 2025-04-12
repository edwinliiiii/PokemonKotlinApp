package com.neu.mobileapplicationdevelopment202430.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamPokemonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToTeam(pokemon: TeamPokemonEntity)

    @Query("SELECT * FROM team")
    fun getTeam(): Flow<List<TeamPokemonEntity>>

    @Query("DELETE FROM team WHERE id = :pokemonId")
    suspend fun removeFromTeam(pokemonId: Int)

    @Query("SELECT COUNT(*) FROM team")
    suspend fun getTeamSize(): Int
}
