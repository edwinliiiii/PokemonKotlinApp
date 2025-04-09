package com.neu.mobileapplicationdevelopment202430.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonNoDelete(products: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon")
    fun getAll(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon WHERE page = :page")
    fun getPokemonByPage(page: Int): Flow<List<PokemonEntity>>

    @Query("DELETE FROM pokemon")
    fun delete()

    @Query("DELETE FROM pokemon WHERE page = :page")
    suspend fun deleteByPage(page: Int)
}