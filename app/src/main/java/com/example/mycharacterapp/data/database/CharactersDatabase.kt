package com.example.mycharacterapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mycharacterapp.data.database.dao.CharactersDao
import com.example.mycharacterapp.data.database.entities.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class CharactersDatabase : RoomDatabase() {
    abstract fun getCharactersDao(): CharactersDao
}