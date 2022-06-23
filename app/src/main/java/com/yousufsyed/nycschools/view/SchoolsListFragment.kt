package com.yousufsyed.nycschools.view

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yousufsyed.nycschools.R
import com.yousufsyed.nycschools.component
import com.yousufsyed.nycschools.dao.data.NycSchool
import com.yousufsyed.nycschools.databinding.FragmentNycSchoolsListBinding
import com.yousufsyed.nycschools.network.data.NycSchoolsResults.*
import com.yousufsyed.nycschools.provider.NetworkConnectivityError
import com.yousufsyed.nycschools.provider.SchoolProvider
import com.yousufsyed.nycschools.view.adapter.NycSchoolsAdapter
import com.yousufsyed.nycschools.viewmodel.NycSchoolsViewModel
import com.yousufsyed.nycschools.viewmodel.NycSchoolsViewModelFactory
import javax.inject.Inject

/**
 * [SchoolsListFragment] as the default destination in the navigation.
 */
class SchoolsListFragment : Fragment(R.layout.fragment_nyc_schools_list) {

    @Inject
    lateinit var nycSchoolsViewModelFactory: NycSchoolsViewModelFactory

    @Inject
    lateinit var schoolProvider: SchoolProvider

    private val nycViewModel: NycSchoolsViewModel by activityViewModels {
        nycSchoolsViewModelFactory
    }

    private var _binding: FragmentNycSchoolsListBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val nycSchoolAdapter: NycSchoolsAdapter by lazy {
        NycSchoolsAdapter(::onSchoolSelected)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        _binding = FragmentNycSchoolsListBinding.bind(requireView())

        with(binding) {
            with(schoolsListRv) {
                adapter = nycSchoolAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
            }
        }
        resetViews()
    }

    private fun initObservers() {
        nycViewModel.nycSchoolsStateLiveData.observe(viewLifecycleOwner) {
            resetViews()
            when (it) {
                Loading -> showLoadingState()
                is Success -> showResultList()
                is Error -> showErrorState(it.throwable)
            }
        }

        nycViewModel.nycSchoolsLiveData.observe(viewLifecycleOwner) {
            if(it.isNullOrEmpty()) {
                showEmptyState()
            }
            nycSchoolAdapter.submitList(it)
        }
    }

    private fun showResultList() {
        with(binding) {
            schoolsListRv.visibility = View.VISIBLE
        }
    }

    private fun showErrorState(error: Throwable) {
        val message = when (error) {
            is NetworkConnectivityError -> getString(R.string.network_error_message)
            // TODO: Show Error messages based on Exceptions
            else -> getString(R.string.generic_error_message)
        }

        with(binding) {
            errorContainer.visibility = View.VISIBLE
            errorTv.text = message

            retry.setOnClickListener {
                nycViewModel.retryFetch()
            }
        }
    }

    private fun showLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        binding.emptyStateTv.visibility = View.VISIBLE
    }

    private fun resetViews() {
        with(binding) {
            progressBar.visibility = View.GONE
            schoolsListRv.visibility = View.GONE
            errorContainer.visibility = View.GONE
            emptyStateTv.visibility = View.GONE
        }
    }

    private fun onSchoolSelected(nycSchool: NycSchool) {
        schoolProvider.set(nycSchool)
        findNavController().navigate(
            SchoolsListFragmentDirections.actionShowSchoolDetails()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}