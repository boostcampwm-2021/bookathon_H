package com.khnsoft.bookathon_h.repository

import android.content.Context
import com.khnsoft.bookathon_h.dto.GithubRepo

interface ProjectRepository {
    fun setProjectList(context: Context, projectList: List<GithubRepo.Project>)
    fun getProjectList(context: Context): List<GithubRepo.Project>
}