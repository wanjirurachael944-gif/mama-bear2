package com.example.mamabear.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Repository for handling authentication-related operations using Firebase.
 */
class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    /**
     * Returns the currently authenticated user, or null if no user is signed in.
     */
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    /**
     * Signs out the current user.
     */
    fun signOut() {
        auth.signOut()
    }
}
