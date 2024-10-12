package com.example.app2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //SETUP
        val bundle = intent.extras
        val email =bundle?.getString("email")
        val provider =bundle?.getString("provider")
        setup(email ?:"",provider?:"")





    }

    private fun setup(email:String,provider:String){
        title="Inicio"
        val emailText: TextView =findViewById(R.id.emailtext)
        val providerText: TextView =findViewById(R.id.providertext)
        val cerrar: Button =findViewById(R.id.cerrarButton)
        emailText.text=email
        providerText.text=provider

        cerrar.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }


    }
}