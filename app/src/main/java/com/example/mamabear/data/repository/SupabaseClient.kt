package com.example.mamabear.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseManager {

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://qvexdyuucsgkhrmljoaf.supabase.co/rest/v1/",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF2ZXhkeXV1Y3Nna2hybWxqb2FmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzgwMzE1MDUsImV4cCI6MjA5MzYwNzUwNX0.olUgyTwvCGeWfLdA1EdfjosMdSTNYT80qAI-zQ4K4jI"
    ) {

        install(Auth)
        install(Postgrest)
    }
}