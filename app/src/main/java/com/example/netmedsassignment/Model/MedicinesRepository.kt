package com.example.netmedsassignment.Model

import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MedicinesRepository {

    private var runnable: Runnable? = null

    fun getMedicines(dataSavedCallback: (isSaved: Boolean) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(Api::class.java)
        val call: Call<List<Medicines>> = api.medicines

        call.enqueue(object : Callback<List<Medicines>> {

            override fun onResponse(
                call: Call<List<Medicines>>,
                response: Response<List<Medicines>>
            ) {
                val medicinesList: List<Medicines>? = response.body()
                if (medicinesList != null) {
                    val medicinesDataList: ArrayList<MedicinesData> = ArrayList()
                    for (medicines in medicinesList) {
                        val medicinesData = MedicinesData()
                        medicinesData.id = medicines.id
                        medicinesData.name = medicines.name
                        medicinesData.type = medicines.type
                        medicinesData.strength = medicines.strength
                        medicinesData.strengthType = medicines.strengthtype
                        medicinesDataList.add(medicinesData)
                    }
                    runnable = Runnable {
                        val medicinesDatabase: MedicinesDatabase? =
                            MedicineGlobal.getMedicineDatabase()

                        medicinesDatabase?.saveDataDao()
                            ?.insertMedicinesData((medicinesDataList))
                        dataSavedCallback.invoke(true)
                    }

                }
                Thread(runnable).start()
                if (response.isSuccessful) {
                    Log.e("Success", Gson().toJson(response.body()))
                } else {
                    dataSavedCallback.invoke(false)
                    Log.e("UnSuccess", Gson().toJson(response.errorBody()))
                }
            }

            override fun onFailure(call: Call<List<Medicines>>, t: Throwable) {
                dataSavedCallback.invoke(false)
                t.message?.let { Log.e("MedicinesRepository", it) }
            }
        })
    }

    fun getMedicinesInBetween(minMedicineId: Int, maxMedicineId: Int, dataListCallback: (dataList: ArrayList<MedicinesData>)-> Unit) {
        val runnable = Runnable {
            val medicineList = (MedicineGlobal.getMedicineDatabase()?.saveDataDao()
                ?.getMedicinesDataByIndex(minMedicineId, maxMedicineId)!! as ArrayList<MedicinesData>)
            dataListCallback.invoke(medicineList)
        }
        Thread(runnable).start()
    }

    fun saveMedicines(ids: ArrayList<Int>, dataSavedCallback: (isSaved: Boolean) -> Unit) {
        val runnable = Runnable {
            MedicineGlobal.getMedicineDatabase()?.saveDataDao()
                ?.saveMedicines(ids)
            dataSavedCallback.invoke(true)
        }
        Thread(runnable).start()
    }

    fun getSavedMedicines(dataListCallback: (dataList: ArrayList<MedicinesData>)-> Unit) {
        val runnable = Runnable {
            val medicineList = MedicineGlobal.getMedicineDatabase()?.saveDataDao()
                ?.getSavedMedicines()
            dataListCallback.invoke(medicineList as ArrayList<MedicinesData>)
        }
        Thread(runnable).start()
    }

    fun deleteSavedMedicines(ids: ArrayList<Int>, dataSavedCallback: (isSaved: Boolean) -> Unit) {
        val runnable = Runnable {
            MedicineGlobal.getMedicineDatabase()?.saveDataDao()
                ?.deleteSavedMedicines(ids)
            dataSavedCallback.invoke(true)
        }
        Thread(runnable).start()
    }

}