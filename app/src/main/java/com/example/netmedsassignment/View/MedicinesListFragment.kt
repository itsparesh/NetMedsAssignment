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
import androidx.recyclerview.widget.RecyclerView
import com.example.netmedsassignment.ViewModel.MedicineViewModel
import com.example.netmedsassignment.Model.MedicinesData
import com.example.netmedsassignment.R
import kotlinx.android.synthetic.main.medicines_list_fragment.*

class MedicinesListFragment : Fragment() {
    private var medicineListAdapter: MedicineListAdapter? = null
    private var medicineList: ArrayList<MedicinesData>? = null
    private var selectedMedicineList: List<MedicinesData>? = null
    private var medicineViewModel: MedicineViewModel? = null
    private var minMedicineId: Int = 0
    private var maxMedicineId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.medicines_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        initializeViewModel()
        setupObserver()
        setUpRecycleView()
        getMedicines()
    }

    private fun setOnClickListener() {
        nextBtn?.setOnClickListener {
            (activity as MainActivity).openPrescriptionListFragment(selectedMedicineList as ArrayList<MedicinesData>?)
        }
    }

    private fun setUpRecycleView() {
        medicinesListRV?.setHasFixedSize(true)
        medicinesListRV?.layoutManager = LinearLayoutManager(context)
        medicinesListRV?.itemAnimator = DefaultItemAnimator()
        medicineListAdapter = MedicineListAdapter()

        medicinesListRV?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastVisiblePosition: Int =
                    (medicinesListRV?.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (lastVisiblePosition == recyclerView.childCount) {
                    minMedicineId += 10
                    maxMedicineId += 10
                    contentProgressBar?.visibility = View.VISIBLE
                    getMedicinesInBetween(minMedicineId + 10, maxMedicineId)
                }
            }
        })
    }

    private fun initializeViewModel() {
        medicineViewModel = ViewModelProvider(this).get(MedicineViewModel::class.java)
    }

    private fun setupObserver() {
        medicineViewModel?.getMedicinesListAsLiveData()?.observe(viewLifecycleOwner, {
            contentProgressBar?.visibility = View.GONE
            medicineList = it
            setAdapter()
        })
    }

    private fun setAdapter() {
        selectedMedicineList = medicineListAdapter?.getSelectedList()
        medicineList?.let { medicineListAdapter?.addData(it) }
        medicinesListRV?.adapter = medicineListAdapter
    }

    private fun getMedicines() {
        contentProgressBar?.visibility = View.VISIBLE
        medicineViewModel?.getMedicines { dataSavedCallback ->
            if (dataSavedCallback) {
                minMedicineId = 0
                maxMedicineId = minMedicineId + 10
                getMedicinesInBetween(minMedicineId, maxMedicineId)
            } else {
                Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getMedicinesInBetween(minMedicineId: Int, maxMedicineId: Int) {
        medicineViewModel?.getMedicinesInBetween(minMedicineId, maxMedicineId)
    }
}