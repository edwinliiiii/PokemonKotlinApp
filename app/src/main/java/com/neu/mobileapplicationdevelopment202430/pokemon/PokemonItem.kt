package com.neu.mobileapplicationdevelopment202430.pokemon

import android.os.Parcelable
import com.neu.mobileapplicationdevelopment202430.room.TeamPokemonEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonItem(
    val id: Int,
    val name: String,
    val type1: String,
    val type2: String?,
    val abilities: String,
    val species: String,
    val description: String,
    val sprite: String,
    val page: Int = 1
) : Parcelable

data class PokemonResponse (
    val page: Int,
    val pokemon: List<PokemonItem>
)

data class PokemonRandomResponse(
    val pokemon: TeamPokemonEntity
)
