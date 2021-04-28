package com.example.netmedsassignment.View

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.netmedsassignment.Model.MedicinesData
import com.example.netmedsassignment.R
import kotlinx.android.synthetic.main.activity_main.*


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
        transaction.replace(
            R.id.fragmentLayout, medicinesListFragment,
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
        transaction.replace(
            R.id.fragmentLayout, prescriptionListFragment,
            PRESCRIPTION_LIST_FRAGMENT_TAG
        )
        transaction.addToBackStack(PRESCRIPTION_LIST_FRAGMENT_TAG)
        transaction.commit()
    }

    fun setToolbarText(text: String) {
        supportActionBar?.title = text
    }

    companion object {
        const val MEDICINES_LIST_FRAGMENT_TAG = "MEDICINES_LIST_FRAGMENT"
        const val PRESCRIPTION_LIST_FRAGMENT_TAG = "PRESCRIPTION_LIST_FRAGMENT_TAG"
        const val SELECTED_LIST = "SELECTED_LIST"
    }
}