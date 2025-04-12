package com.neu.mobileapplicationdevelopment202430.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team")
data class TeamPokemonEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type1") val type1: String,
    @ColumnInfo(name = "type2") val type2: String?,
    @ColumnInfo(name = "abilities") val abilities: String,
    @ColumnInfo(name = "species")  val species: String,
    @ColumnInfo(name = "description")  val description: String,
    @ColumnInfo(name = "sprite") val sprite: String
)
