package pe.edu.ulima.pm.goutsidevf

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import pe.edu.ulima.pm.goutsidevf.Model.LoginManager
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

class SignupActivity : AppCompatActivity() {

    private val dbFirebase = Firebase.firestore
    private var photoPath : String? = null
    lateinit var storage: FirebaseStorage

    //-----------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val eteNewName = findViewById<EditText>(R.id.eteNewName)
        val eteNewUsername = findViewById<EditText>(R.id.eteNewUsername)
        val eteNewPassword= findViewById<EditText>(R.id.eteNewPassword)
        storage = FirebaseStorage.getInstance()

        //-------------------------------------------------------
        photoPath = getPreferences(MODE_PRIVATE).getString("USER_PHOTO", "")
        if(photoPath !=""){
            showphoto()
        }
        findViewById<Button>(R.id.butNewRegister).setOnClickListener { v : View ->//

            // -- Busqueda de Usuario en FB

            val query = dbFirebase.collection("users").whereEqualTo("username", eteNewUsername.text.toString())
            query.get().addOnSuccessListener {
                if(it.isEmpty){
                    uploadImage { url: String ->
                        LoginManager.instance.saveUser(
                            eteNewName.text.toString(),
                            eteNewUsername.text.toString(),
                            eteNewPassword.text.toString(),
                            url,
                            {
                                //Si logramos una creacion de documento exitosa...
                                //Enviamos una respuesta al LoginActivity
                                val bundle = Bundle()
                                bundle.putString(
                                    "username",
                                    findViewById<EditText>(R.id.eteNewUsername).text.toString()
                                )
                                bundle.putString(
                                    "password",
                                    findViewById<EditText>(R.id.eteNewPassword).text.toString()
                                )

                                val intent = Intent(this, LoginActivity::class.java)
                                intent.putExtra("signup_data", bundle)
                                setResult(RESULT_OK, intent)
                                finish()
                            },
                            {   //Si ocurre un error...
                                Log.e("SignupActivity", it)
                                Toast.makeText(this, "Error guardando usuario", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        )
                    }

                }
                else{
                    Log.i("mensajeError","Este Usuario ya esta registrado")
                    Toast.makeText(this, "Usuario ya registrado", Toast.LENGTH_LONG).show()
                    eteNewName.setText("")
                    eteNewUsername.setText("")
                    eteNewPassword.setText("")
                }

            }.addOnFailureListener {
                Log.i("mensajeError","Ocurrio un Error buscando en FB")
                Toast.makeText(this, "Error en la busqueda", Toast.LENGTH_LONG).show()
            }
        }

        //-------------------Image Picker------------------
        findViewById<ImageView>(R.id.iviPickIcon).setOnClickListener {
            takePhoto()
        }
        //----------------------Cancel---------------------
        findViewById<Button>(R.id.butCancel).setOnClickListener { _ : View ->
            // Cancelamos el Registro
            setResult(RESULT_CANCELED)
            finish()
        }
    }


    private fun uploadImage(resultado: (String)->Unit) {
        var storageRef = storage.reference
        val file = Uri.fromFile(File(photoPath))
        val riversRef = storageRef.child("images/${file.lastPathSegment}")
        val uploadTask = riversRef.putFile(file)
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Log.e("UploadError", it.toString())
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.

        }

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            riversRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                resultado(downloadUri.toString())

            } else {
                Log.e("UploadCompleteError", task.toString())
            }

        }

    }

    fun takePhoto() {
        var imageFile : File? = null
        try {
            imageFile = createImageFile()
        }catch (ioe : IOException) {
            Log.e("FotoActivity", "No se pudo crear archivo de imagen")
            return
        }

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoURI = FileProvider.getUriForFile(
            this,
            "pe.edu.ulima.goutsidevf.fileprovider",
            imageFile
        )

        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(intent, 200)

    }
    @Throws(IOException::class)
    fun createImageFile() : File {
        val timestamp = SimpleDateFormat("yyyyMMddd_HHmmss").format(Date())
        val imageFile = File.createTempFile(
            "${timestamp}_",
            ".jpg",
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        photoPath = imageFile.absolutePath
        return imageFile
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==200 && resultCode== RESULT_OK){
            showphoto()
        }

    }

    private fun showphoto() {
        val options = BitmapFactory.Options()
        val bitmap = BitmapFactory.decodeFile(photoPath,options )
        findViewById<ImageView>(R.id.iviPickIcon).setImageBitmap(bitmap)
    }
}
