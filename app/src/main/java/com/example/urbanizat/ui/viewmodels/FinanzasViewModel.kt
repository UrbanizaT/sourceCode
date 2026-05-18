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
data class EstadisticaData(
    val id: Long = 0,
    val comunity_id: Long = 0,
    val concept: String = "",
    val value: Double = 0.0,
    val month: String = ""
)

@Serializable
data class EstadisticaInsert(
    val comunity_id: Long,
    val concept: String,
    val value: Double,
    val month: String
)

class FinanzasViewModel : ViewModel() {

    private val _movimientos = MutableStateFlow<List<EstadisticaData>>(emptyList())
    val movimientos: StateFlow<List<EstadisticaData>> = _movimientos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _esPresidente = MutableStateFlow(false)
    val esPresidente: StateFlow<Boolean> = _esPresidente

    private var comunityId: Long = 0

    init {
        loadMovimientos()
    }

    fun loadMovimientos() {
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

                val comunidad = supabase.from("comunity")
                    .select { filter { eq("code", usuario.comunity_code) } }
                    .decodeList<ComunityData>()
                    .firstOrNull() ?: return@launch

                comunityId = comunidad.id

                _movimientos.value = supabase.from("estadistica")
                    .select { filter { eq("comunity_id", comunityId) } }
                    .decodeList<EstadisticaData>()

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteMovimiento(id: Long) {
        viewModelScope.launch {
            try {
                supabase.from("estadistica").delete { filter { eq("id", id) } }
                loadMovimientos()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun createMovimiento(concepto: String, valor: Double, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val today = java.time.LocalDate.now().toString()
                supabase.from("estadistica").insert(
                    EstadisticaInsert(
                        comunity_id = comunityId,
                        concept = concepto,
                        value = valor,
                        month = today
                    )
                )
                loadMovimientos()
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
