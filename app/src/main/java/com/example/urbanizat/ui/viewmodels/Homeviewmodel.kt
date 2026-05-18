package com.example.urbanizat.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urbanizat.utils.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import io.github.jan.supabase.auth.SignOutScope

@Serializable
data class UsuarioData(
    val id: Long = 0,
    val email: String = "",
    val name: String = "",
    val door: String = "",
    val comunity_code: Long = 0,
    val rol: String = "VECINO"
)

@Serializable
data class ComunityData(
    val id: Long = 0,
    val name: String = "",
    val code: Long = 0
)

class HomeViewModel : ViewModel() {

    private val _userName = MutableStateFlow("Usuario")
    val userName: StateFlow<String> = _userName

    private val _communityName = MutableStateFlow("")
    val communityName: StateFlow<String> = _communityName

    init {
        loadUserData()
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                supabase.auth.signOut(SignOutScope.LOCAL)
                onSuccess()
            } catch (e: Exception) {
                supabase.auth.signOut(SignOutScope.LOCAL)
                onSuccess()
            }
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                // Esperar a que la sesión esté lista
                val session = supabase.auth.currentSessionOrNull()
                val email = session?.user?.email ?: run {
                    // Si no hay sesión, intentar recuperarla
                    supabase.auth.awaitInitialization()
                    supabase.auth.currentSessionOrNull()?.user?.email
                } ?: return@launch

                // Buscar datos del usuario en public.usuario
                val usuarios = supabase.from("usuario")
                    .select {
                        filter {
                            eq("email", email)
                        }
                    }
                    .decodeList<UsuarioData>()

                val usuario = usuarios.firstOrNull() ?: return@launch
                _userName.value = usuario.name.ifBlank { email }

                // Buscar nombre de la comunidad
                if (usuario.comunity_code > 0) {
                    val comunidades = supabase.from("comunity")
                        .select {
                            filter {
                                eq("code", usuario.comunity_code)
                            }
                        }
                        .decodeList<ComunityData>()

                    _communityName.value = comunidades.firstOrNull()?.name ?: ""
                }

            } catch (e: Exception) {
                _userName.value = "ERR: ${e.message?.take(50)}"
            }
        }
    }
}