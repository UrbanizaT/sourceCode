package com.example.urbanizat.utils

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://yxzhgcymrhmtztlgisgf.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inl4emhnY3ltcmhtdHp0bGdpc2dmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzE0MDU1NzMsImV4cCI6MjA4Njk4MTU3M30.5aAppfuq-Id2HsEvrTlJfjexZUlOmIsceYgRH6j1u8E"
) {
    install(Auth) {
        alwaysAutoRefresh = true
        autoLoadFromStorage = true
    }
    install(Postgrest)
}
