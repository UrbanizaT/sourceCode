package com.example.urbanizat.domain.repository

import com.example.urbanizat.data.remote.dto.UserDto
import com.example.urbanizat.domain.model.User
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class UserRepositoryImpl  @Inject constructor(
    private val postgrest: Postgrest,
    private val storage: Storage,
): UserRepository {
    override suspend fun createUser(user: User): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                val userDto = UserDto(
                    id = user.id,
                    name = user.name,
                    email = user.email,
                    door = user.door,
                    comunity_code = user.comunity_code
                )
                postgrest.from("users").insert(userDto)
                true
            }
            true
        } catch (e: java.lang.Exception) {
            throw e
        }
    }
}
