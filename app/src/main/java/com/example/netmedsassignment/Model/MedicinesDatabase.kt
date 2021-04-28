package com.example.netmedsassignment.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MedicinesData::class], version = 1, exportSchema = false)
abstract class MedicinesDatabase: RoomDatabase() {

    abstract fun saveDataDao(): MedicinesDataDao

    companion object {
        @Volatile
        private var INSTANCE: MedicinesDatabase? = null

        fun getDatabase(context: Context): MedicinesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MedicinesDatabase::class.java,
                    "medicines_database.db"
                ).fallbackToDestructiveMigration().
                build()
                INSTANCE = instance
                instance
            }
        }
    }
}