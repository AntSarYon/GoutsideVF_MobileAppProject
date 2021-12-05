package pe.edu.ulima.pm.goutsidevf

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pe.edu.ulima.pm.goutsidevf.Model.LoginManager

class SignupActivity : AppCompatActivity() {

    private val dbFirebase = Firebase.firestore

    //-----------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val eteNewName = findViewById<EditText>(R.id.eteNewName)
        val eteNewUsername = findViewById<EditText>(R.id.eteNewUsername)
        val eteNewPassword= findViewById<EditText>(R.id.eteNewPassword)

        //-------------------------------------------------------

        findViewById<Button>(R.id.butNewRegister).setOnClickListener { v : View ->//

            // -- Busqueda de Usuario en FB
            val query = dbFirebase.collection("users").whereEqualTo("username", eteNewUsername.text.toString())
            query.get().addOnSuccessListener {

                Log.i("mensajeError","Este Usuario ya esta registrado")
                Toast.makeText(this, "Usuario ya registrado", Toast.LENGTH_LONG).show()
                eteNewName.setText("")
                eteNewUsername.setText("")
                eteNewPassword.setText("")


            }.addOnFailureListener {

                // En caso No haya mismo usuario, guardamos los Datos en Firebase
                LoginManager.instance.saveUser(
                    eteNewName.text.toString(),
                    eteNewUsername.text.toString(),
                    eteNewPassword.text.toString(),
                    {
                        //Si logramos una creacion de documento exitosa...
                        //Enviamos una respuesta al LoginActivity
                        val bundle = Bundle()
                        bundle.putString("username", findViewById<EditText>(R.id.eteNewUsername).text.toString())
                        bundle.putString("password", findViewById<EditText>(R.id.eteNewPassword).text.toString())

                        val intent = Intent(this, LoginActivity::class.java)
                        intent.putExtra("signup_data", bundle)
                        setResult(RESULT_OK, intent)
                        finish()
                    },
                    {   //Si ocurre un error...
                        Log.e("SignupActivity", it)
                        Toast.makeText(this, "Error guardando usuario", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }


        //-------------------------------------------------------
        findViewById<Button>(R.id.butCancel).setOnClickListener { _ : View ->
            // Cancelamos el Registro
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}
