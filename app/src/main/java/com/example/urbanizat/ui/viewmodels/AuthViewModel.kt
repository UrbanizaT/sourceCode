package com.example.urbanizat.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urbanizat.utils.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@Serializable
data class RGPDInsert(
    val user_id: Long,
    val register_date: String,
    val accept: Boolean,
    val content: String
)

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(emailInput: String, passwordInput: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                supabase.auth.signInWith(Email) {
                    email = emailInput
                    password = passwordInput
                }
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error(
                    message = e.message ?: "Error: ${e.javaClass.simpleName}"
                )
            }
        }
    }

    fun register(
        emailInput: String,
        passwordInput: String,
        nombreInput: String,
        puertaInput: String,
        codigoInput: String
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val codigoLong = codigoInput.toLongOrNull()
                if (codigoLong == null) {
                    _authState.value = AuthState.Error("El código de comunidad debe ser numérico")
                    return@launch
                }

                // El trigger on_auth_user_created se encarga de insertar
                // automáticamente en public.usuario al crear el usuario en auth.users
                supabase.auth.signUpWith(Email) {
                    email = emailInput
                    password = passwordInput
                    data = buildJsonObject {
                        put("name", nombreInput)
                        put("door", puertaInput)
                        put("comunity_code", codigoLong)
                    }
                }

                // Guardar aceptación RGPD
                try {
                    val usuario = supabase.from("usuario")
                        .select { filter { eq("email", emailInput) } }
                        .decodeList<com.example.urbanizat.ui.viewmodels.UsuarioData>()
                        .firstOrNull()
                    if (usuario != null) {
                        supabase.from("RGPD").insert(
                            RGPDInsert(
                                user_id = usuario.id,
                                register_date = java.time.LocalDate.now().toString(),
                                accept = true,
                                content = "Política de privacidad aceptada en el registro"
                            )
                        )
                    }
                } catch (_: Exception) {}

                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error(
                    message = e.message ?: "Error: ${e.javaClass.simpleName}"
                )
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}
