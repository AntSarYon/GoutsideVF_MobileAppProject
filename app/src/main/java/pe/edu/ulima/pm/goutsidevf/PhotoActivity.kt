package pe.edu.ulima.pm.goutsidevf

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

class PhotoActivity : AppCompatActivity() {

    private lateinit var iviFoto : ImageView
    private var photoPath : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        iviFoto = findViewById(R.id.iviFoto)

        findViewById<Button>(R.id.butTomarFoto).setOnClickListener{
            takePhoto()
        }
        photoPath = getPreferences(MODE_PRIVATE).getString("PHOTO_PATH","")
        if (photoPath != "") {
            showPhoto()
        }
    }


    fun showCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try{
            startActivityForResult(intent, 100)
        }catch (e: ActivityNotFoundException){
            Toast.makeText(this, "No hay aplicativo de camara", Toast.LENGTH_SHORT).show()
        }
    }


    fun takePhoto(){
        var imageFile : File? = null
        try{
            imageFile = createImageFile()
        }catch(ioe : IOException){
            Log.e("FotoActivity", "No se puedo crear sin una imagen")
            return
        }

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoURI = FileProvider.getUriForFile(this, "pe.edu.ulima.goutsidevf.fileprovider", imageFile!!)

        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(intent, 200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode== RESULT_OK){
            val imagen : Bitmap = data!!.extras!!.get("data") as Bitmap
            iviFoto.setImageBitmap(imagen)
        }else if(requestCode == 200 && resultCode== RESULT_OK){
            showPhoto()
        }
    }

    private fun showPhoto() {

        // -- Rotacion de Imagen
        val matrix = Matrix()
        val angle = getRotateAngle()
        matrix.postRotate(angle)

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true

        BitmapFactory.decodeFile(photoPath, options)

        //-- Obtecion de dimensiones para el escalado
        val iviHeight = iviFoto.height
        val iviWidth = iviFoto.width

        // -- Calculo del escalado
        var scaleFactor = 1
        if (angle == 90f || angle == 270f)
            scaleFactor = Math.min(iviWidth / options.outHeight, iviHeight / options.outWidth)
        else
            scaleFactor = Math.min(iviWidth / options.outWidth, iviHeight / options.outHeight)

        options.inJustDecodeBounds = false
        options.inSampleSize = scaleFactor

        // -- Mostramos imagen rotada
        val bitmap = BitmapFactory.decodeFile(photoPath,options)
        val bitmapRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        iviFoto.setImageBitmap(bitmapRotated)
    }

    private fun getRotateAngle() : Float{
        val exifInterface = ExifInterface(photoPath!!)
        val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED)
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
            return 90f
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
            return 180f
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
            return 270f
        return 0f
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("FotoActivity", "onConfigurationChanged")
        getPreferences(MODE_PRIVATE).edit().putString("PHOTO_PATH", photoPath!!).commit()
    }
}