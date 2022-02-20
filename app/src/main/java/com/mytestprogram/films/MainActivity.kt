package com.mytestprogram.films

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mytestprogram.films.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val signInLauncher = registerForActivityResult(  // Создали объект инициализации экрана
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)  // Запуск самого экрана
    }

    private lateinit var database: DatabaseReference // Создали объект записи в БД

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentToMoviesActivity = Intent(this, MoviesActivity::class.java)
        startActivity(intentToMoviesActivity)

//        database = Firebase.database("https://films-5c4b6-default-rtdb.europe-west1.firebasedatabase.app/").reference // инициализация базы данный
//
//        val providers = arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build()) //Список регистрации, который мы используем
//
//// Create and launch sign-in intent
//        val signInIntent = AuthUI.getInstance()
//            .createSignInIntentBuilder()
//            .setAvailableProviders(providers)
//            .build() // Создали интент для экрана Firebase auth
//        signInLauncher.launch(signInIntent)  // Запустили экран Firebase auth
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val authUser = FirebaseAuth.getInstance().currentUser
            authUser?.let {
                val firebaseUser = User(email = it.email.toString(), uid = it.uid)
                database.child("users").child(it.uid).setValue(firebaseUser)

                val intentToMoviesActivity = Intent(this, MoviesActivity::class.java)
                startActivity(intentToMoviesActivity)
            }

        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}