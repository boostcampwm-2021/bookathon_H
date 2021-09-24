package com.khnsoft.bookathon_h.viewmodel

import androidx.lifecycle.ViewModel
import com.khnsoft.bookathon_h.model.ProjectManager
import com.khnsoft.bookathon_h.repository.SharedPreferencesProjectRepository

class ProjectViewModel: ViewModel() {
    val manager = ProjectManager(SharedPreferencesProjectRepository)
}