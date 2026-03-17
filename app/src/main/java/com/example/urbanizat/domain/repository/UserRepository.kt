package com.example.urbanizat.domain.repository

import com.example.urbanizat.domain.model.User

interface UserRepository {
    suspend fun createUser(user: User): Boolean
}