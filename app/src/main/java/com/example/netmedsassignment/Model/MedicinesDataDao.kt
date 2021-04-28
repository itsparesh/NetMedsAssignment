package com.example.netmedsassignment.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MedicinesDataDao {
    @Insert
    fun insertMedicinesData(saveData: List<MedicinesData>)

    @Query("Select * from MedicinesData")
    fun getMedicinesData(): List<MedicinesData>?

    @Query("Select * from (Select * from MedicinesData order by medicineId) where medicineId BETWEEN :minMedicineId AND :maxMedicineId")
    fun getMedicinesDataByIndex(minMedicineId: Int, maxMedicineId: Int): List<MedicinesData>?

    @Query("UPDATE MedicinesData SET saved = 1 WHERE medicineId IN (:ids)")
    fun saveMedicines(ids:List<Int>)

    @Query("Select * from MedicinesData WHERE saved = 1")
    fun getSavedMedicines(): List<MedicinesData>?

    @Query("UPDATE MedicinesData SET saved = 0 WHERE medicineId IN (:ids)")
    fun deleteSavedMedicines(ids:List<Int>)
}