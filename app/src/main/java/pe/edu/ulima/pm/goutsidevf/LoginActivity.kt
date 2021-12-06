package pe.edu.ulima.pm.goutsidevf

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//Data de Login para el Shared Preference
//
data class LoginInfo(                   //
    val username:String,                //
    val loginDate: Date                 //
)                                       //
//---------------------------------------

class LoginActivity : AppCompatActivity() {

    private lateinit var eteUsername : EditText
    private lateinit var etePassword : EditText

    private val dbFirebase = Firebase.firestore

    //-----------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //-- Revisamos si es que ya hay un usuario Logeado --
        if(isLogued()){
            val username = getLoginInfo()[0]
            val name = getLoginInfo()[1]
            changeActivity(username, name)
        }
        //---------------------------------------------------

        eteUsername = findViewById(R.id.eteUsername)
        etePassword = findViewById(R.id.etePassword)

        //-- Funcionalidad del Boton Login --
        val butLogin : Button = findViewById(R.id.butLogin)
        butLogin.setOnClickListener { _ : View ->

            // -- Verificacion de si el Login es Correcto -------------

            // -- Busqueda de Usuario en FB
            val query = dbFirebase.collection("users").whereEqualTo("username", eteUsername.text.toString())
            query.get().addOnSuccessListener {

                val findUser = it.documents[0]

                //Verificamos si la contraseña es correcta
                if (findUser["password"].toString() == etePassword.text.toString()){
                    //Almacenmas la Info del UsuarioLogueado y cambiamos el Activity (implicito)
                    Log.i("mensajeBueno","EXITO")
                    almacenarInfoLogin(findUser["username"].toString(), findUser["name"].toString())

                }
                else{
                    Log.i("mensajeError","La contraseña No es correcta")
                    Toast.makeText(this, "La contraseña es incorrecta", Toast.LENGTH_LONG).show()
                    //Dejamos en vacío los campos
                    eteUsername.setText("")
                    etePassword.setText("")
                }

            }.addOnFailureListener {
                Log.i("mensajeError","No se encontro al ususario")
                Toast.makeText(this, "No se encontro al usuario", Toast.LENGTH_LONG).show()
            }



        }

        //-- Funcionalidad del Boton Registrar --
        val butRegister : Button = findViewById(R.id.butRegister)
        butRegister.setOnClickListener { _ : View ->

            //-- Intent al Activity Signup --> Se espera ResultCode
            val intent : Intent = Intent(this, SignupActivity::class.java)
            startActivityForResult(intent, 10)
        }
    }

    //-----------------------------------------------------------------------------
    // -- Retorno de Respuesta del ActivityRegister ----------------------------------
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 10)
        {
            // -- Caso signup exitoso
            if (resultCode == RESULT_OK){
                val username = data?.getBundleExtra("signup_data")?.getString("username")
                val password = data?.getBundleExtra("signup_data")?.getString("password")
                eteUsername.setText(username)
                etePassword.setText(password)
            }

            // -- Caso Signup Fallido
            else{
                //Registro No exitoso
                eteUsername.setText("")
                etePassword.setText("")
            }
        }
    }

    //-----------------------------------------------------------------------------
    // -- Revisar si esta Loggeado ----------------------------------
    private fun isLogued() : Boolean {
        val sp = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE)
        val username = sp.getString("LOGIN_USERNAME", "")!!

        if (username == ""){
            return false
        }

        val data = sp.getLong("LOGIN_DATE", 0)
        val currentDate = Date().time

        if (currentDate > data+300_000){
            return false
        }

        return true
    }

    //-----------------------------------------------------------------------------
    // -- Obtener Info (Name y Username) de Usuario ----------------------------------
    fun getLoginInfo(): Array<String> {
        val sp = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE)
        val username = sp.getString("LOGIN_USERNAME", "")!!
        val name = sp.getString("LOGIN_NAME", "")!!
        return arrayOf(username, name)
    }

    //-----------------------------------------------------------------------------
    // -- Almacenar Info de Login en un SharedPreference ----------------------------------
    private fun almacenarInfoLogin(username: String, name : String){
        val editor = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE).edit()
        editor.putString("LOGIN_USERNAME", username)
        editor.putString("LOGIN_NAME", name)
        editor.putLong("LOGIN_DATE", Date().getTime())
        editor.commit() //apply()

        //Una vez que se ha almacenado la info del usuario, cambiamos el Activity
        changeActivity(username, name)
    }

    //-----------------------------------------------------------------------------
    // -- Cambiar a otro Activity, manteniendo la info del usuario ----------------------------------
    private fun changeActivity(username:String, name:String){
        val intent : Intent = Intent()
        intent.setClass(this, MainActivity::class.java)

        val bundle : Bundle = Bundle()
        bundle.putString("username", username)
        bundle.putString("name", name)

        intent.putExtra("data",bundle)
        startActivity(intent)
    }

}