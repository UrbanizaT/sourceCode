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
data class NormativaData(
    val id: Long = 0,
    val comunity_id: Long = 0,
    val title: String = "",
    val body: String = ""
)

@Serializable
data class NormativaInsert(
    val comunity_id: Long,
    val title: String,
    val body: String
)

@Serializable
data class NormativaUpdate(
    val title: String,
    val body: String
)

class NormativaViewModel : ViewModel() {

    private val _normativas = MutableStateFlow<List<NormativaData>>(emptyList())
    val normativas: StateFlow<List<NormativaData>> = _normativas

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _esPresidente = MutableStateFlow(false)
    val esPresidente: StateFlow<Boolean> = _esPresidente

    private var comunityId: Long = 0

    init {
        loadNormativas()
    }

    fun loadNormativas() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                supabase.auth.awaitInitialization()
                val session = supabase.auth.currentSessionOrNull() ?: run {
                    _error.value = "No hay sesión activa"
                    return@launch
                }
                val email = session.user?.email ?: run {
                    _error.value = "No se pudo obtener el email"
                    return@launch
                }

                val usuario = supabase.from("usuario")
                    .select { filter { eq("email", email) } }
                    .decodeList<UsuarioData>()
                    .firstOrNull() ?: run {
                    _error.value = "Usuario no encontrado"
                    return@launch
                }

                _esPresidente.value = usuario.rol == "PRESIDENTE"

                val comunidad = supabase.from("comunity")
                    .select { filter { eq("code", usuario.comunity_code) } }
                    .decodeList<ComunityData>()
                    .firstOrNull() ?: run {
                    _error.value = "Comunidad no encontrada"
                    return@launch
                }

                comunityId = comunidad.id

                val lista = supabase.from("normativa")
                    .select { filter { eq("comunity_id", comunityId) } }
                    .decodeList<NormativaData>()

                _normativas.value = lista

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createNormativa(titulo: String, cuerpo: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                supabase.from("normativa").insert(
                    NormativaInsert(
                        comunity_id = comunityId,
                        title = titulo,
                        body = cuerpo
                    )
                )
                loadNormativas()
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun updateNormativa(id: Long, titulo: String, cuerpo: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                supabase.from("normativa")
                    .update(NormativaUpdate(title = titulo, body = cuerpo)) {
                        filter { eq("id", id) }
                    }
                loadNormativas()
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun deleteNormativa(id: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                supabase.from("normativa")
                    .delete { filter { eq("id", id) } }
                loadNormativas()
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
