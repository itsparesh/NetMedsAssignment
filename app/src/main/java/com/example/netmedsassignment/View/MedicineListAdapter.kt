package com.example.netmedsassignment.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netmedsassignment.Model.MedicinesData
import com.example.netmedsassignment.R
import com.example.netmedsassignment.View.MedicineListAdapter.DataViewHolder
import kotlinx.android.synthetic.main.medicine_item_layout.view.*

class MedicineListAdapter: RecyclerView.Adapter<DataViewHolder>() {

    private var medicinesList: MutableList<MedicinesData>? = mutableListOf()
    private var selectedMedicinesList: MutableList<MedicinesData>? = mutableListOf()

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(positionItem: MedicinesData?) {
            itemView.medicinesTV?.text = positionItem?.name
            itemView.medicineCB?.isChecked = positionItem?.isChecked == true
            itemView.medicineCB?.setOnCheckedChangeListener { _, p1 ->
                positionItem?.isChecked = p1
                if (p1) {
                    positionItem?.let { selectedMedicinesList?.add(it) }
                } else {
                    positionItem?.let { selectedMedicinesList?.remove(it) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.medicine_item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = medicinesList?.size ?: 0

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(medicinesList?.get(position))

    fun addData(list: MutableList<MedicinesData>) {
        medicinesList?.addAll(list)
    }

    fun getSelectedList(): MutableList<MedicinesData>? {
        return selectedMedicinesList
    }
}