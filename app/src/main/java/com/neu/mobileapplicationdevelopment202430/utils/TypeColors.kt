package com.neu.mobileapplicationdevelopment202430.utils

import androidx.compose.ui.graphics.Color

val typeColors = mapOf(
    "normal" to Color(0xFFA8A878),
    "fire" to Color(0xFFF08030),
    "water" to Color(0xFF6890F0),
    "electric" to Color(0xFFF8D030),
    "grass" to Color(0xFF78C850),
    "ice" to Color(0xFF98D8D8),
    "fighting" to Color(0xFFC03028),
    "poison" to Color(0xFFA040A0),
    "ground" to Color(0xFFE0C068),
    "flying" to Color(0xFFA890F0),
    "psychic" to Color(0xFFF85888),
    "bug" to Color(0xFFA8B820),
    "rock" to Color(0xFFB8A038),
    "ghost" to Color(0xFF705898),
    "dragon" to Color(0xFF7038F8),
)

fun getTypeColor(type: String): Color {
    return typeColors[type.lowercase()] ?: Color.Black
}