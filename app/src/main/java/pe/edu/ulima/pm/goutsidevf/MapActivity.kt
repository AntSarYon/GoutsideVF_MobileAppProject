package pe.edu.ulima.pm.goutsidevf

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pe.edu.ulima.pm.goutsidevf.Model.Event

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private val dbFirebase = Firebase.firestore

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    private var latitud: String? = null
    private var longitud: String? = null
    private var nomb: String? = null
    private var latitudS: String? = null
    private var longitudS: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        latitud = intent.getBundleExtra("location_data")?.getString("latitud")?.toString()
        longitud = intent.getBundleExtra("location_data")?.getString("longitud")?.toString()
        nomb = intent.getBundleExtra("location_data")?.getString("nombre")?.toString()
        latitudS = intent.getBundleExtra("location_selected")?.getString("latitud")?.toString()
        longitudS = intent.getBundleExtra("location_selected")?.getString("longitud")?.toString()
        setContentView(R.layout.activity_map)

        createMapFragment()
    }

    private fun createMapFragment() {
        val mapFragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarkers()
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        enableLocation()

    }

    private fun createMarkers() {

        //CREACION DE MARKERS EN BASE A Documentos de Firebase
        dbFirebase.collection("locations").get().addOnSuccessListener { res ->
            for (document in res) {

                val latitud = document.data["latitud"]!! as Double
                val longitud = document.data["longitud"]!! as Double
                val coordenadas = LatLng(latitud, longitud)

                val nombre = document.data["nombre"]!! as String
                val newMarker = MarkerOptions().position(coordenadas).title(nombre)

                map.addMarker(newMarker)
                    ?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            }
        }.addOnFailureListener {
            Log.i("Error", "No se exraño ni se marco nada de FB")
        }

        //CREACION DE MARKERS EN BASE A USUARIO (ULima)
        val cooULIMA = LatLng(-12.084847, -76.973097)
        val nombre = "Universidad de Lima"
        val mkULIMA = MarkerOptions().position(cooULIMA).title(nombre)

        //Añadir Marcador de la Universidad de Lima
        map.addMarker(mkULIMA)

        if (nomb != null) {
            val markLocation = MarkerOptions()
                .position(LatLng(latitud!!.toDouble(), longitud!!.toDouble()))
                .title("Evento: ${nomb}")
            map.addMarker(markLocation)
                ?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        }


        //-- Que haga un Zoom a un Marcador

        //Si no hay coordenadas, apuntar hacia ULIMA
        if (latitud != null && longitud != null) {
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(latitud!!.toDouble(), longitud!!.toDouble()), 14f
                ), 4000, null
            )
        } else if (latitudS != null && longitudS != null) {
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(latitudS!!.toDouble(), longitudS!!.toDouble()), 18f
                ), 4000, null
            )
        } else {
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(cooULIMA, 18f), 4000, null
            )

        }

    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) === PackageManager.PERMISSION_GRANTED


    private fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(this, "Ve a ajustes y acepta", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Ve a ajustes y acepta", Toast.LENGTH_SHORT).show()
            }
            else ->{}
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized) return
        if(!isLocationPermissionGranted()){
            map.isMyLocationEnabled = false
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "Ubicando...", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "Ubicado en: ${p0.latitude},${p0.longitude}", Toast.LENGTH_SHORT).show()
    }

}