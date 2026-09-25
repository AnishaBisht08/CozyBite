package com.example.cozybite.data.repository

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import com.example.cozybite.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore


class AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()


    fun registerUser(
        username: String,
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                val uid = auth.currentUser?.uid ?: ""

                val user = User(
                    uid = uid,
                    username = username,
                    email = email
                )

                db.collection("users")
                    .document(uid)
                    .set(user)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            onResult(false, null)
                        } else {
                            onResult(true, it.exception?.message)
                        }
                    }

            }
            .addOnFailureListener {
                onResult(true, it.message)
            }

    }

    fun loginUser(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onResult(false, null)
                } else {
                    onResult(true, it.exception?.message)
                }


            }
            .addOnFailureListener {
                onResult(true, it.message)
            }

    }


    fun signInWithGoogle(
        idToken:String,
        onResult: (Boolean) -> Unit
    ){

        val credential = GoogleAuthProvider.getCredential(idToken,null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }



    suspend fun logout(context: Context) {
        FirebaseAuth.getInstance().signOut()

        val credentialManager = CredentialManager.create(context)

        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
    }


}