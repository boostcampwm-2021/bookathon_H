package com.khnsoft.bookathon_h

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.khnsoft.bookathon_h.databinding.FragmentStatisticsBinding
import com.khnsoft.bookathon_h.viewmodel.ProjectViewModel

class StatisticsFragment : Fragment() {
    private val projectViewModel by lazy { ViewModelProvider(this).get(ProjectViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val adapter = StatisticsAdapter()
        binding.recyclerView.adapter = adapter
        projectViewModel.manager.addProject("AAA")
        projectViewModel.manager.addTask("AAA", "aaa")
        projectViewModel.manager.flattenProjectList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
//        // check box test...
//        binding.averageTextView.setOnClickListener {
//            adapter.notifyDataSetChanged()
//        }
        return binding.root
    }

}