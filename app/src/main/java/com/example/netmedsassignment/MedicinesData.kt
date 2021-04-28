package com.example.netmedsassignment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "MedicinesData")
class MedicinesData(): Serializable {
    @ColumnInfo(name = "medicineId")
    @PrimaryKey(autoGenerate = true)
    var medicineId: Int = 0

    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "Name")
    var name: String = ""

    @ColumnInfo(name = "type")
    var type: String = ""

    @ColumnInfo(name = "company")
    var company: String = ""

    @ColumnInfo(name = "strength")
    var strength: String = ""

    @ColumnInfo(name = "strengthtype")
    var strengthType: String = ""

    @ColumnInfo(name = "saved")
    var isSaved: Boolean = false

    var isChecked: Boolean = false

    constructor(saveData: MedicinesData) : this() {
        this.id = saveData.id
        this.name = saveData.name
        this.type = saveData.type
        this.company = saveData.company
        this.strength = saveData.strength
        this.strengthType = saveData.strengthType
        this.isSaved = saveData.isSaved
    }
}