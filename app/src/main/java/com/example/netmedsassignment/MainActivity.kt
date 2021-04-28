package com.example.netmedsassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private val medicinesListFragment: MedicinesListFragment = MedicinesListFragment()
    private val prescriptionListFragment: PrescriptionListFragment = PrescriptionListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openMedicineListFragment()
    }

    fun openMedicineListFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentLayout, medicinesListFragment,
            MEDICINES_LIST_FRAGMENT_TAG
        )
        transaction.addToBackStack(MEDICINES_LIST_FRAGMENT_TAG)
        transaction.commit()
    }

    fun openPrescriptionListFragment(selectedMedicineList: ArrayList<MedicinesData>?) {
        val transaction = supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable(SELECTED_LIST, selectedMedicineList)
        prescriptionListFragment.arguments = bundle
        transaction.replace(R.id.fragmentLayout, prescriptionListFragment,
            PRESCRIPTION_LIST_FRAGMENT_TAG
        )
        transaction.addToBackStack(PRESCRIPTION_LIST_FRAGMENT_TAG)
        transaction.commit()
    }

    companion object {
        const val MEDICINES_LIST_FRAGMENT_TAG = "MEDICINES_LIST_FRAGMENT"
        const val PRESCRIPTION_LIST_FRAGMENT_TAG = "PRESCRIPTION_LIST_FRAGMENT_TAG"
        const val SELECTED_LIST = "SELECTED_LIST"
    }
}