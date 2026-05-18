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
data class EventoSupabase(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val start_date: String = "",
    val end_date: String = "",
    val calendar_id: Long = 0,
    val tipo: String = "reunion"
)

@Serializable
data class CalendarioData(
    val id: Long = 0,
    val comunity_id: Long = 0
)

@Serializable
data class EventoInsert(
    val title: String,
    val description: String,
    val start_date: String,
    val end_date: String,
    val calendar_id: Long,
    val tipo: String
)

class CalendarioViewModel : ViewModel() {

    private val _eventos = MutableStateFlow<List<EventoSupabase>>(emptyList())
    val eventos: StateFlow<List<EventoSupabase>> = _eventos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _esPresidente = MutableStateFlow(false)
    val esPresidente: StateFlow<Boolean> = _esPresidente

    private var calendarId: Long = 0

    init {
        loadEventos()
    }

    fun loadEventos() {
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

                // Obtener el id real de la comunidad (no el code)
                val comunidad = supabase.from("comunity")
                    .select { filter { eq("code", usuario.comunity_code) } }
                    .decodeList<ComunityData>()
                    .firstOrNull() ?: return@launch

                val calendario = supabase.from("Calendario")
                    .select { filter { eq("comunity_id", comunidad.id) } }
                    .decodeList<CalendarioData>()
                    .firstOrNull() ?: return@launch

                calendarId = calendario.id

                _eventos.value = supabase.from("eventos")
                    .select { filter { eq("calendar_id", calendarId) } }
                    .decodeList<EventoSupabase>()

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteEvento(id: Long) {
        viewModelScope.launch {
            try {
                supabase.from("eventos").delete { filter { eq("id", id) } }
                loadEventos()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun createEvento(titulo: String, descripcion: String, fecha: String, tipo: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                supabase.from("eventos").insert(
                    EventoInsert(
                        title = titulo,
                        description = descripcion,
                        start_date = fecha,
                        end_date = fecha,
                        calendar_id = calendarId,
                        tipo = tipo
                    )
                )
                loadEventos()
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
