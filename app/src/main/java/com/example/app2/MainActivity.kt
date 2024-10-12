package com.example.app2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var fireStore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle=Bundle()
        bundle.putString("message","Integracion de firebase completa")
        analytics.logEvent("InitScreen", bundle)

        setup()

    }

    private fun setup(){
        title="Autenticacion"
        val sign: Button = findViewById(R.id.signButton)
        val login: Button=findViewById(R.id.loginButton)
        val email:EditText =findViewById(R.id.emaileditext)
        val pass:EditText =findViewById(R.id.passwordeditext)



        sign.setOnClickListener{
            if(email.text.isNotEmpty() && pass.text.isNotEmpty()){

               FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
                   .addOnCompleteListener { task ->
                       if (task.isSuccessful) {
                           showhome(task.result?.user?.email ?:"",ProviderType.BASIC)
                       } else {
                          showAlert()
                       }
                   }
            }


        }

     login.setOnClickListener{
         if (email.text.isNotEmpty() && pass.text.isNotEmpty()) {
             FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(), pass.text.toString())
                 .addOnCompleteListener { task ->
                     if (task.isSuccessful) {
                         showhome(task.result?.user?.email ?: "", ProviderType.BASIC)
                     } else {
                         showAlert()
                     }
                 }
         }

     }





    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("error")
        builder.setMessage("se ha producido un error autenticando el usuario")
        val dialog: AlertDialog=builder.create()
        dialog.show()

    }
    private fun showhome(email:String ,provider:ProviderType){

        val homeIntent:Intent = Intent(this,HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)

    }


}