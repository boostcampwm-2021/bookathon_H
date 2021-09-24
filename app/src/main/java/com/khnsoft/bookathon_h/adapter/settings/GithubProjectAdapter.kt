package com.khnsoft.bookathon_h.adapter.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.khnsoft.bookathon_h.R
import com.khnsoft.bookathon_h.dto.GithubProject

class GithubProjectAdapter(private val githubProjectList: List<GithubProject>): RecyclerView.Adapter<GithubProjectAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val projectCheckBox = view.findViewById<CheckBox>(R.id.projectCheckBox)

        init {
            projectCheckBox.setOnCheckedChangeListener { _, isChecked ->
                githubProjectList[adapterPosition].checked = isChecked
            }
        }

        fun bind(project: GithubProject) {
            projectCheckBox.text = project.name
            projectCheckBox.isChecked = project.checked
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_github_project, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(githubProjectList[position])
    }

    override fun getItemCount(): Int = githubProjectList.size
}