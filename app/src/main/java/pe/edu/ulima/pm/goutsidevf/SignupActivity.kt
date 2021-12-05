package pe.edu.ulima.pm.goutsidevf

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pe.edu.ulima.pm.goutsidevf.Model.LoginManager

class SignupActivity : AppCompatActivity() {

    //-----------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //-------------------------------------------------------

        findViewById<Button>(R.id.butNewRegister).setOnClickListener { v : View ->//
            //Gaurdamos los Datos en Firebase
            LoginManager.instance.saveUser(
                findViewById<EditText>(R.id.eteNewName).text.toString(),
                findViewById<EditText>(R.id.eteNewUsername).text.toString(),
                findViewById<EditText>(R.id.eteNewPassword).text.toString(),
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

        //-------------------------------------------------------
        findViewById<Button>(R.id.butCancel).setOnClickListener { _ : View ->
            // Cancelamos el Registro
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}