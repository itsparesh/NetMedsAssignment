package com.example.netmedsassignment.Model

import android.app.Application
import android.content.Context

class MedicineGlobal: Application() {
    companion object {
        private var medicinesDatabase: MedicinesDatabase? = null
        private var medicineGlobal: Context? = null

        fun getInstance(): Context? {
            return medicineGlobal
        }

        fun getMedicineDatabase(): MedicinesDatabase? {
            if (medicinesDatabase == null) {
                medicinesDatabase = medicineGlobal?.let { MedicinesDatabase.getDatabase(it) }
            }
            return medicinesDatabase
        }
    }

    override fun onCreate() {
        super.onCreate()
        medicineGlobal = applicationContext
    }
}