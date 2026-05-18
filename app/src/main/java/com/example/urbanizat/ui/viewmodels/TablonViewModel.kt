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

@Serializable
data class NotificacionData(
    val id: Long = 0,
    val comunity_id: Long = 0,
    val user_id: Long = 0,
    val title: String = "",
    val message: String = "",
    val created_at: String = ""
)

@Serializable
data class NotificacionInsert(
    val comunity_id: Long,
    val user_id: Long,
    val title: String,
    val message: String
)

class TablonViewModel : ViewModel() {

    private val _anuncios = MutableStateFlow<List<NotificacionData>>(emptyList())
    val anuncios: StateFlow<List<NotificacionData>> = _anuncios

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _esPresidente = MutableStateFlow(false)
    val esPresidente: StateFlow<Boolean> = _esPresidente

    private var comunityId: Long = 0
    private var userId: Long = 0

    init {
        loadAnuncios()
    }

    fun loadAnuncios() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                supabase.auth.awaitInitialization()
                val session = supabase.auth.currentSessionOrNull() ?: return@launch
                val email = session.user?.email ?: return@launch

                val usuario = supabase.from("usuario")
                    .select { filter { eq("email", email) } }
                    .decodeList<UsuarioData>()
                    .firstOrNull() ?: return@launch

                _esPresidente.value = usuario.rol == "PRESIDENTE"
                userId = usuario.id

                val comunidad = supabase.from("comunity")
                    .select { filter { eq("code", usuario.comunity_code) } }
                    .decodeList<ComunityData>()
                    .firstOrNull() ?: return@launch

                comunityId = comunidad.id

                _anuncios.value = supabase.from("notificaciones")
                    .select { filter { eq("comunity_id", comunityId) } }
                    .decodeList<NotificacionData>()

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createAnuncio(titulo: String, mensaje: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                supabase.from("notificaciones").insert(
                    NotificacionInsert(
                        comunity_id = comunityId,
                        user_id = userId,
                        title = titulo,
                        message = mensaje
                    )
                )
                loadAnuncios()
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
