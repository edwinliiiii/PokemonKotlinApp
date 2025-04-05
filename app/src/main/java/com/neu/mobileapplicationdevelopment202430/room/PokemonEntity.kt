package com.neu.mobileapplicationdevelopment202430.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type1") val type1: String,
    @ColumnInfo(name = "type2") val type2: String?,
//    @ColumnInfo(name = "abilities")
//    @TypeConverters(StringListConverter::class)
//    val abilities: List<String>,
    @ColumnInfo(name = "species")  val species: String,
    @ColumnInfo(name = "description")  val description: String,
    @ColumnInfo(name = "sprite") val sprite: String,
    @ColumnInfo(name = "page") val page:Int = 1
)

fun PokemonEntity.toPokemonItem(): PokemonItem {
    return PokemonItem(
        id = this.id,
        name = this.name,
        type1 = this.type1,
        type2 = this.type2,
//        abilities = this.abilities,
        species = this.species,
        description = this.description,
        sprite = this.sprite,
        page = this.page
    )
}

fun PokemonItem.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = this.id,
        name = this.name,
        type1 = this.type1,
        type2 = this.type2,
//        abilities = this.abilities,
        species = this.species,
        description = this.description,
        sprite = this.sprite,
        page = this.page
    )
}

class StringListConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return value?.split(",")?.map { it.trim() } ?: emptyList()
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return list.joinToString(",")
    }
}