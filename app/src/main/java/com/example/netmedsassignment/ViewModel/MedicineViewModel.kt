package com.example.netmedsassignment.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netmedsassignment.Model.MedicinesData
import com.example.netmedsassignment.Model.MedicinesRepository

class MedicineViewModel : ViewModel() {

    private val medicinesRepository: MedicinesRepository by lazy {
        MedicinesRepository()
    }

    private var medicinesList: MutableLiveData<ArrayList<MedicinesData>>? =
        MutableLiveData<ArrayList<MedicinesData>>()

    fun getMedicinesListAsLiveData(): MutableLiveData<ArrayList<MedicinesData>>? {
        return medicinesList
    }

    fun getMedicinesInBetween(minMedicineId: Int, maxMedicineId: Int) {
        medicinesRepository.getMedicinesInBetween(minMedicineId, maxMedicineId) {
            medicinesList?.postValue(it)
        }
    }

    fun getMedicines(dismissalCallback: (isLibertyCFSelected: Boolean) -> Unit) {
        medicinesRepository.getMedicines {
            dismissalCallback.invoke(it)
        }
    }

    fun saveMedicines(ids: List<Int>, dataSavedCallback: (isSaved: Boolean) -> Unit) {
        medicinesRepository.saveMedicines(ids as ArrayList<Int>) {
            dataSavedCallback.invoke(it)
        }
    }

    fun getSavedMedicines(dataListCallback: (dataList: ArrayList<MedicinesData>)-> Unit) {
        medicinesRepository.getSavedMedicines {
            dataListCallback.invoke(it)
        }
    }

    fun deleteSavedMedicines(ids: List<Int>, dataSavedCallback: (isSaved: Boolean) -> Unit) {
        medicinesRepository.deleteSavedMedicines(ids as ArrayList<Int>) {
            dataSavedCallback.invoke(it)
        }
    }
}