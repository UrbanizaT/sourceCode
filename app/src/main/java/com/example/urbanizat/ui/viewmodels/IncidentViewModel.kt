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
data class Incidencia(
    val id: Long = 0,
    val comunity_id: Long = 0,
    val user_id: Long = 0,
    val type: String = "",
    val description: String = "",
    val lugar: String = "",
    val estado: String? = null
)

@Serializable
data class IncidenciaInsert(
    val comunity_id: Long,
    val user_id: Long,
    val type: String,
    val description: String,
    val lugar: String,
    val estado: String = "pendiente"
)

sealed class IncidentState {
    data object Idle : IncidentState()
    data object Loading : IncidentState()
    data object Success : IncidentState()
    data class Error(val message: String) : IncidentState()
}

class IncidentViewModel : ViewModel() {

    private val _incidencias = MutableStateFlow<List<Incidencia>>(emptyList())
    val incidencias: StateFlow<List<Incidencia>> = _incidencias

    private val _state = MutableStateFlow<IncidentState>(IncidentState.Idle)
    val state: StateFlow<IncidentState> = _state

    private val _esPresidente = MutableStateFlow(false)
    val esPresidente: StateFlow<Boolean> = _esPresidente

    private var comunityId: Long = 0
    private var userId: Long = 0

    init {
        loadIncidencias()
    }

    fun loadIncidencias() {
        viewModelScope.launch {
            _state.value = IncidentState.Loading
            try {
                supabase.auth.awaitInitialization()

                val session = supabase.auth.currentSessionOrNull()
                if (session == null) {
                    _state.value = IncidentState.Error("No hay sesión activa")
                    return@launch
                }

                val email = session.user?.email
                if (email == null) {
                    _state.value = IncidentState.Error("No se pudo obtener el email")
                    return@launch
                }

                // Buscar usuario por email
                val usuario = supabase.from("usuario")
                    .select { filter { eq("email", email) } }
                    .decodeList<UsuarioData>()
                    .firstOrNull()

                if (usuario == null) {
                    _state.value = IncidentState.Error("Usuario no encontrado")
                    return@launch
                }

                userId = usuario.id
                _esPresidente.value = usuario.rol == "PRESIDENTE"

                // Obtener el id REAL de la comunidad desde su code
                val comunidad = supabase.from("comunity")
                    .select { filter { eq("code", usuario.comunity_code) } }
                    .decodeList<ComunityData>()
                    .firstOrNull()

                if (comunidad == null) {
                    _state.value = IncidentState.Error("Comunidad no encontrada")
                    return@launch
                }

                comunityId = comunidad.id  // id real, NO el code

                // Cargar incidencias filtrando por comunity_id real
                val lista = supabase.from("incidencia")
                    .select { filter { eq("comunity_id", comunityId) } }
                    .decodeList<Incidencia>()

                _incidencias.value = lista
                _state.value = IncidentState.Idle

            } catch (e: Exception) {
                _state.value = IncidentState.Error(e.message ?: "Error al cargar incidencias")
            }
        }
    }

    fun createIncidencia(tipo: String, lugar: String, descripcion: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _state.value = IncidentState.Loading
            try {
                supabase.from("incidencia").insert(
                    IncidenciaInsert(
                        comunity_id = comunityId,
                        user_id = userId,
                        type = tipo,
                        description = descripcion,
                        lugar = lugar
                    )
                )
                _state.value = IncidentState.Success
                loadIncidencias()
                onSuccess()
            } catch (e: Exception) {
                _state.value = IncidentState.Error(e.message ?: "Error al crear incidencia")
            }
        }
    }

    fun updateEstado(id: Long, nuevoEstado: String) {
        viewModelScope.launch {
            try {
                supabase.from("incidencia")
                    .update({ set("estado", nuevoEstado) }) {
                        filter { eq("id", id) }
                    }
                loadIncidencias()
            } catch (e: Exception) {
                _state.value = IncidentState.Error(e.message ?: "Error al actualizar")
            }
        }
    }

    fun resetState() {
        _state.value = IncidentState.Idle
    }
}