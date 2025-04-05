package com.neu.mobileapplicationdevelopment202430.pokemon

data class PokemonItem(
    val id: Int,
    val name: String,
    val type1: String,
    val type2: String?,
//    val abilities: List<String>,
    val species: String,
    val description: String,
    val sprite: String,
    val page: Int = 1
)

data class PokemonResponse (
    val page: Int,
    val pokemon: List<PokemonItem>
)