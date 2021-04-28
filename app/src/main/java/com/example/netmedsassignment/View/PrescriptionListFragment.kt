package com.example.netmedsassignment.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netmedsassignment.ViewModel.MedicineViewModel
import com.example.netmedsassignment.Model.MedicinesData
import com.example.netmedsassignment.R
import kotlinx.android.synthetic.main.prescription_pad_fragement.*

class PrescriptionListFragment : Fragment() {

    private var prescriptionListAdapter: PrescriptionListAdapter? = null
    private var medicineList: ArrayList<MedicinesData>? = null
    private var savedMedicineList: ArrayList<MedicinesData>? = null
    private var selectedMedicineList: List<Int>? = null
    private var medicineViewModel: MedicineViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.prescription_pad_fragement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setToolbarText(getString(R.string.prescription))
        initializeViewModel()
        getSavedList()
        setUpRecycleView()
        setOnClickListener()
    }

    private fun getListArguments() {
        val tempList: ArrayList<MedicinesData>? = arguments?.getSerializable(MainActivity.SELECTED_LIST) as ArrayList<MedicinesData>?
        tempList?.let { medicineList?.addAll(it) }
        val set: LinkedHashSet<MedicinesData> = LinkedHashSet(savedMedicineList)
        tempList?.let { set.addAll(it) }
        val combinedList: ArrayList<MedicinesData> = ArrayList(set)
        setAdapter(combinedList)
    }

    private fun getSavedList() {
        medicineViewModel?.getSavedMedicines {
            savedMedicineList = it
            getListArguments()
        }
    }

    private fun saveMedicines(isSavedCallback: (isDeleted: Boolean)-> Unit) {
        prescriptionListAdapter?.getSelectedList()?.let { isSaved ->
            medicineViewModel?.saveMedicines(isSaved) {
                activity?.runOnUiThread {
                    if (it) {
                        Toast.makeText(activity, getString(R.string.saved), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show()
                    }
                    isSavedCallback(it)
                }
            }
        }
    }

    private fun deleteSavedMedicines(isDeletedCallback: (isDeleted: Boolean)-> Unit) {
        prescriptionListAdapter?.getSelectedList()?.let { isSaved ->
            medicineViewModel?.deleteSavedMedicines(isSaved) {
                activity?.runOnUiThread {
                    if (it) {
                        Toast.makeText(activity, getString(R.string.deleted), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, getString(R.string.deleteFailed), Toast.LENGTH_SHORT).show()
                    }
                    isDeletedCallback.invoke(it)
                }
            }
        }
    }

    private fun setUpRecycleView() {
        prescriptionRV?.setHasFixedSize(true)
        prescriptionRV?.layoutManager = LinearLayoutManager(context)
        prescriptionRV?.itemAnimator = DefaultItemAnimator()
        prescriptionListAdapter = PrescriptionListAdapter()
    }

    private fun setAdapter(combinedList: ArrayList<MedicinesData>?) {
        selectedMedicineList = prescriptionListAdapter?.getSelectedList()
        combinedList?.let { prescriptionListAdapter?.addData(it) }
        prescriptionRV?.adapter = prescriptionListAdapter
        prescriptionListAdapter?.notifyDataSetChanged()
    }

    private fun initializeViewModel() {
        medicineViewModel = ViewModelProvider(this).get(MedicineViewModel::class.java)
    }

    private fun setOnClickListener() {
        saveBtn?.setOnClickListener {
            if (prescriptionListAdapter?.getSelectedList().isNullOrEmpty()) {
                Toast.makeText(activity, getString(R.string.selectItemsFirst), Toast.LENGTH_SHORT).show()
            } else {
                saveMedicines {
                    if (it) {
                        (activity as MainActivity).supportFragmentManager.popBackStack()
                    }
                }
            }
        }
        deleteBtn?.setOnClickListener {
            if (prescriptionListAdapter?.getSelectedList().isNullOrEmpty()) {
                Toast.makeText(activity, getString(R.string.selectItemsFirst), Toast.LENGTH_SHORT).show()
            } else {
                deleteSavedMedicines {
                    if (it) {
                        (activity as MainActivity).supportFragmentManager.popBackStack()
                    }
                }
            }
        }
    }
}