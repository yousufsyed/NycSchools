package com.yousufsyed.nycschools.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yousufsyed.nycschools.R
import com.yousufsyed.nycschools.component
import com.yousufsyed.nycschools.dao.data.NycSchool
import com.yousufsyed.nycschools.databinding.FragmentSchoolDetailsBinding
import com.yousufsyed.nycschools.provider.SchoolProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SchoolDetailsFragment : Fragment(R.layout.fragment_school_details) {

    @Inject lateinit var schoolProvider: SchoolProvider

    private var _binding: FragmentSchoolDetailsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        initViews()
        initObservers()
    }

    private fun initViews() {
        _binding = FragmentSchoolDetailsBinding.bind(requireView())
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                schoolProvider.get().collect { school ->
                    school?.let {
                        bind(it)
                    }
                }
            }
        }
    }

    private fun bind(nycSchool: NycSchool) {
        with(binding) {
            schoolName.text = nycSchool.name
            schoolLocation.text = nycSchool.location
            schoolPhone.text = nycSchool.phone
            schoolWebsite.text = nycSchool.website
            schoolEmail.text = nycSchool.email
            schoolOverview.text = nycSchool.overview
            satTestTakers.text = getString(R.string.total_test_takers, nycSchool.totalTestTakers)
            satMath.text = getString(R.string.avg_math_score, nycSchool.satMathScore)
            satWriting.text = getString(R.string.avg_writing_scores, nycSchool.satWritingScore)
            satReading.text = getString(R.string.avg_reading_scores, nycSchool.criticalReadingScore)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}