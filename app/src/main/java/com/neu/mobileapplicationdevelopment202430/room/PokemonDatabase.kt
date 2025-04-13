package com.neu.mobileapplicationdevelopment202430.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PokemonEntity::class, TeamPokemonEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun teamPokemonDao(): TeamPokemonDao

    companion object {
        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        fun getDatabase(
            context: Context
        ): PokemonDatabase {
            if (INSTANCE != null) {
                return INSTANCE as PokemonDatabase
            }
            else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PokemonDatabase::class.java,
                        "pokemon_database")
                        .fallbackToDestructiveMigration()
                    val builtDatabase = instance.build()
                    INSTANCE = builtDatabase
                    return INSTANCE as PokemonDatabase
                }
            }
        }
    }
}