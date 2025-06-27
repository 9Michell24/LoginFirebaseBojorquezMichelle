package mx.edu.potros.ejemploauthfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import mx.edu.potros.ejemploauthfirebase.R

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = Firebase.auth

        val email: EditText=findViewById(R.id.etrEmail)
        val password: EditText=findViewById(R.id.etrPassword)
        val confirmPassword: EditText=findViewById(R.id.etrConfirmPassword)
        val errorTv: TextView =findViewById(R.id.tvrError)
        val button: Button=findViewById(R.id.btnRegister)

        errorTv.visibility= View.INVISIBLE

        button.setOnClickListener {
            if (email.text.isEmpty() || password.text.isEmpty() || confirmPassword.text.isEmpty()) {
                errorTv.text = "Todos los campos son obligatorios."
                errorTv.visibility = View.VISIBLE
            }else if (!password.text.toString().equals(confirmPassword.text.toString())) {
                errorTv.text = "Las contraseñas no coinciden."
                errorTv.visibility = View.VISIBLE
            }else{
                    errorTv.visibility = View.INVISIBLE
                    signIn(email.text.toString(), password.text.toString())
            }


        }

        }



    fun signIn (email: String, password: String){
        Log.d( "INFO", "email: ${email}, password: ${password}")
        auth.createUserWithEmailAndPassword (email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information Log.d(tag: "INFO", msg: "signInWithEmail: success")
                    Log.d("INFO", "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent( this, MainActivity::class.java)
                    intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("ERROR","signInWithEmail: failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "El registro falló.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}
