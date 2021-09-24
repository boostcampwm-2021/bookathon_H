package com.khnsoft.bookathon_h

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khnsoft.bookathon_h.adapter.statistics.StatisticsAdapter
import com.khnsoft.bookathon_h.databinding.FragmentStatisticsBinding
import com.khnsoft.bookathon_h.viewmodel.ProjectViewModel

class StatisticsFragment : Fragment() {
    private val viewModel: ProjectViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                ProjectViewModel(context) as T
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val adapter = StatisticsAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.manager.addProject("AAA")
        viewModel.manager.addTask("AAA", "aaa")
        viewModel.manager.flattenProjectList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
//        // check box test...
//        binding.averageTextView.setOnClickListener {
//            adapter.notifyDataSetChanged()
//        }
        return binding.root
    }

}