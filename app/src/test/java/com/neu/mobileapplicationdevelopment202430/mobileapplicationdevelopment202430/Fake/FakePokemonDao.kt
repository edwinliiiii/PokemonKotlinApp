package com.neu.mobileapplicationdevelopment202430.Fake

import com.neu.mobileapplicationdevelopment202430.room.PokemonDao
import com.neu.mobileapplicationdevelopment202430.room.PokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class FakePokemonDao : PokemonDao {

    private val pokemonData = MutableStateFlow<List<PokemonEntity>>(emptyList())

    fun setInitialData(initialData: List<PokemonEntity>) {
        pokemonData.value = initialData
    }

    fun getCurrentDataSnapshot(): List<PokemonEntity> = pokemonData.value

    override suspend fun insertAll(products: List<PokemonEntity>) {
        val currentMap = pokemonData.value.associateBy { it.id }.toMutableMap()
        products.forEach { product ->
            currentMap[product.id] = product
        }
        pokemonData.value = currentMap.values.toList()
    }

    override suspend fun insertPokemonNoDelete(products: List<PokemonEntity>) {
        val currentList = pokemonData.value
        val existingIds = currentList.map { it.id }.toSet()
        val productsToAdd = products.filterNot { existingIds.contains(it.id) }

        if (productsToAdd.isNotEmpty()) {
            pokemonData.value = currentList + productsToAdd
        }
    }

    override fun getAll(): Flow<List<PokemonEntity>> {
        return pokemonData.asStateFlow()
    }

    override fun getPokemonByPage(page: Int): Flow<List<PokemonEntity>> {
        return pokemonData.map { list -> list.filter { it.page == page } }
    }

    override fun delete() {
        pokemonData.value = emptyList()
    }

    override suspend fun deleteByPage(page: Int) {
        pokemonData.value = pokemonData.value.filterNot { it.page == page }
    }
}