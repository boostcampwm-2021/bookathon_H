package com.khnsoft.bookathon_h.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khnsoft.bookathon_h.R

class SettingsItemAdapter : ListAdapter<SettingsItem, RecyclerView.ViewHolder>(SettingsItemDiffCallback()) {
    companion object {
        private const val TYPE_PROJECT = 0
        private const val TYPE_TASK = 1
        private const val TYPE_ADD = 2
    }

    class ProjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val container = view.findViewById<LinearLayout>(R.id.container)
        private val nameTextView = view.findViewById<TextView>(R.id.nameTextView)

        fun bind(item: SettingsItem.ProjectItem) {
            container.setOnClickListener { item.onClickListener.onClick() }
            nameTextView.text = item.project.name
        }

        companion object {
            fun from(parent: ViewGroup): ProjectViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_settings_project, parent, false)
                return ProjectViewHolder(view)
            }
        }
    }

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val container = view.findViewById<LinearLayout>(R.id.container)
        private val nameTextView = view.findViewById<TextView>(R.id.nameTextView)

        fun bind(item: SettingsItem.TaskItem) {
            container.setOnClickListener { item.onClickListener.onClick() }
            nameTextView.text = item.task.name
        }

        companion object {
            fun from(parent: ViewGroup): TaskViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_settings_task, parent, false)
                return TaskViewHolder(view)
            }
        }
    }

    class AddViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val container = view.findViewById<LinearLayout>(R.id.container)

        fun bind(item: SettingsItem.AddItem) {
            container.setOnClickListener { item.onClickListener.onClick() }
        }

        companion object {
            fun from(parent: ViewGroup): AddViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_settings_new, parent, false)
                return AddViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is SettingsItem.ProjectItem -> TYPE_PROJECT
        is SettingsItem.TaskItem -> TYPE_TASK
        is SettingsItem.AddItem -> TYPE_ADD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        TYPE_PROJECT -> ProjectViewHolder.from(parent)
        TYPE_TASK -> TaskViewHolder.from(parent)
        else -> AddViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProjectViewHolder -> holder.bind(getItem(position) as SettingsItem.ProjectItem)
            is TaskViewHolder -> holder.bind(getItem(position) as SettingsItem.TaskItem)
            is AddViewHolder -> holder.bind(getItem(position) as SettingsItem.AddItem)
        }
    }
}