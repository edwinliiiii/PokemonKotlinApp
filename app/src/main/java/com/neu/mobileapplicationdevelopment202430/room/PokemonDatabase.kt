package com.neu.mobileapplicationdevelopment202430.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [PokemonEntity::class], version = 2)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                try {
                    db.execSQL("SELECT page FROM pokemon LIMIT 1")
                } catch (e: Exception) {
                    db.execSQL("ALTER TABLE pokemon ADD COLUMN page INTEGER NOT NULL DEFAULT 1")
                }
            }
        }
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
                        .addMigrations(MIGRATION_1_2)
                    val builtDatabase = instance.build()
                    INSTANCE = builtDatabase
                    return INSTANCE as PokemonDatabase
                }
            }
        }
    }
}