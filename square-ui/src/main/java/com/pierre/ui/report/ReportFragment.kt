package com.pierre.ui.report

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.pierre.ui.base.BaseFragment
import com.pierre.ui.databinding.FragmentReportBinding
import com.pierre.ui.report.adapter.ReportAdapter
import com.pierre.ui.report.models.ReportMotion
import com.pierre.ui.report.models.ReportState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportFragment : BaseFragment() {

    private val reportViewModel: ReportViewModel by viewModels()

    private lateinit var binding: FragmentReportBinding

    override fun initBinding(inflater: LayoutInflater): ViewBinding {
        binding = FragmentReportBinding.inflate(inflater)
        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()

        // Listen ui state events
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { reportViewModel.state.collect { handleState(it) } }
            }
        }

        reportViewModel.getMotions()
    }

    // Display the right ui depending on the state
    private fun handleState(state: ReportState) {
        when (state) {
            is ReportState.ReportErrorState -> displayError(state.message) { reportViewModel.getMotions() }
            is ReportState.ReportLoadingState -> displayLoading(true)
            is ReportState.ReportResultsState -> displayResults(state)
        }
    }

    // Shows the paged data
    private fun displayResults(resultsState: ReportState.ReportResultsState) {
        displayLoading(false)
        (binding.reports.adapter as? ReportAdapter)?.submitData(viewLifecycleOwner.lifecycle, resultsState.pagedReports)
    }

    // Only show a dialog for now...
    private fun onReportClicked(report: ReportMotion) {
        AlertDialog.Builder(context)
            .setTitle("Square / Touch X and Y")
            .setMessage(report.positions.toString())
            .show()
    }

    companion object {
        const val DEEPLINK_URI = "movingsquare://com.pierre.square/report"
    }

    private fun configureRecyclerView() {
        binding.reports.apply {
            adapter = ReportAdapter(::onReportClicked)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}