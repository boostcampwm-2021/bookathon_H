package com.khnsoft.bookathon_h.repository

import com.khnsoft.bookathon_h.dto.GithubProject

interface GithubRepository {
    fun getRepositoryNamesOf(username: String): List<GithubProject>
}