package com.neu.mobileapplicationdevelopment202430.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.neu.mobileapplicationdevelopment202430.product.PokemonResult
import com.neu.mobileapplicationdevelopment202430.room.IPokemonRepository
import com.neu.mobileapplicationdevelopment202430.room.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PokemonViewModelFactory(private val repository: IPokemonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class PokemonListViewModel (val repository: IPokemonRepository): ViewModel() {
    val pokemon = MutableStateFlow<PokemonResult<List<PokemonItem>>>(PokemonResult.Loading)
    val pagedPokemon: Flow<PagingData<PokemonItem>> = repository.getPagedPokemon().cachedIn(viewModelScope)
}