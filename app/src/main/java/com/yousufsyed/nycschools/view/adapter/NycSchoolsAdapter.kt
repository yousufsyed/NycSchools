package com.yousufsyed.nycschools.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yousufsyed.nycschools.dao.data.NycSchool
import com.yousufsyed.nycschools.databinding.NycSchoolItemBinding
import com.yousufsyed.nycschools.inflater
import com.yousufsyed.nycschools.view.adapter.NycSchoolsAdapter.NycSchoolViewHolder

class NycSchoolsAdapter(
    private val action: (NycSchool) -> Unit
) : ListAdapter<NycSchool, NycSchoolViewHolder>(NycSchoolsDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NycSchoolViewHolder {
        val binding = NycSchoolItemBinding.inflate(parent.inflater)
        return NycSchoolViewHolder(binding, action)
    }

    override fun onBindViewHolder(holder: NycSchoolViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NycSchoolViewHolder(
        private val binding: NycSchoolItemBinding,
        private val action: (NycSchool) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(nycSchool: NycSchool) {
            with(binding) {
                schoolName.text = nycSchool.name
                schoolLocation.text = nycSchool.location
                schoolPhone.text = nycSchool.phone
                root.setOnClickListener {
                    action(nycSchool)
                }
            }
        }
    }
}

class NycSchoolsDiffUtils : DiffUtil.ItemCallback<NycSchool>() {
    override fun areItemsTheSame(oldItem: NycSchool, newItem: NycSchool): Boolean =
        oldItem.schoolId == newItem.schoolId

    override fun areContentsTheSame(oldItem: NycSchool, newItem: NycSchool): Boolean =
        oldItem.schoolId == newItem.schoolId
}