package com.example.urbanizat.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urbanizat.utils.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ComunidadViewModel : ViewModel() {

    private val _vecinos = MutableStateFlow<List<UsuarioData>>(emptyList())
    val vecinos: StateFlow<List<UsuarioData>> = _vecinos

    private val _communityName = MutableStateFlow("")
    val communityName: StateFlow<String> = _communityName

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadVecinos()
    }

    fun loadVecinos() {
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

                val comunidad = supabase.from("comunity")
                    .select { filter { eq("code", usuario.comunity_code) } }
                    .decodeList<ComunityData>()
                    .firstOrNull() ?: return@launch

                _communityName.value = comunidad.name

                _vecinos.value = supabase.from("usuario")
                    .select { filter { eq("comunity_code", usuario.comunity_code) } }
                    .decodeList<UsuarioData>()

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
