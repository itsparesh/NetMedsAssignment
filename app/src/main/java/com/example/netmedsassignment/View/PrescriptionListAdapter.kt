package com.example.netmedsassignment.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netmedsassignment.Model.MedicineGlobal
import com.example.netmedsassignment.Model.MedicinesData
import com.example.netmedsassignment.R
import com.example.netmedsassignment.View.PrescriptionListAdapter.PrescriptionDataViewHolder
import kotlinx.android.synthetic.main.medicine_item_layout.view.*

class PrescriptionListAdapter: RecyclerView.Adapter<PrescriptionDataViewHolder>() {

    private var medicinesList: MutableList<MedicinesData>? = mutableListOf()
    private var selectedMedicinesList: MutableList<Int>? = mutableListOf()

    inner class PrescriptionDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(positionItem: MedicinesData?) {
            itemView.medicinesTV?.text = positionItem?.name
            val dosageText = (1..3).random().toString() + " " + MedicineGlobal.getInstance()
                ?.getString(R.string.perDay)
            itemView.dosageTV?.text = dosageText
            itemView.medicineCB?.setOnCheckedChangeListener { _, p1 ->
                if (p1) {
                    positionItem?.let { selectedMedicinesList?.add(it.medicineId) }
                } else {
                    positionItem?.let { selectedMedicinesList?.remove(it.medicineId) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PrescriptionDataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.medicine_item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = medicinesList?.size ?: 0

    override fun onBindViewHolder(holder: PrescriptionDataViewHolder, position: Int) =
        holder.bind(medicinesList?.get(position))

    fun addData(list: MutableList<MedicinesData>) {
        medicinesList?.addAll(list)
    }

    fun getSelectedList(): MutableList<Int>? {
        return selectedMedicinesList
    }
}