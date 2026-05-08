package com.jayr.supaauth.data.repositories

import android.net.http.HttpResponseCache.install
import com.example.mamabear.data.models.UserModel
import com.example.mamabear.data.repository.AuthService
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.json.buildJsonObject
import java.sql.DriverManager.println

class AuthRepository: AuthService {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://qvexdyuucsgkhrmljoaf.supabase.co/rest/v1/",
        supabaseKey = "sb_publishable_lFiOR1_4-B7mU4D_Pk0Cwg_58Du7Llc"
    )  {
        install(Postgrest)
        install(Auth)
    }


    override suspend fun registerUser(userDetails: UserModel)  {
        supabase.auth.signUpWith(Email) {
            email = userDetails.email
            password = userDetails.password
        }
    }

    override suspend fun loginUser(userDetails: UserModel)  {
        val user = supabase.auth.signInWith(Email) {
            email = userDetails.email
            password = userDetails.password
        }
    }

    override suspend fun resetPassword(email: String) {
        supabase.auth.resetPasswordForEmail(email = email)
    }

    override suspend fun getUserProfile(user: UserModel) {
//        TODO("Not yet implemented")
    }

    override suspend fun logoutUser() {
        supabase.auth.signOut()
    }

}