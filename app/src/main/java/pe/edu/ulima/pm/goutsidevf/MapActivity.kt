package pe.edu.ulima.pm.goutsidevf

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pe.edu.ulima.pm.goutsidevf.Model.Event

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val dbFirebase = Firebase.firestore

    private lateinit var map: GoogleMap

    private var latitud : String? = null
    private var longitud : String? = null
    private var nomb : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        latitud = intent.getBundleExtra("location_data")?.getString("latitud")?.toString()
        longitud = intent.getBundleExtra("location_data")?.getString("longitud")?.toString()
        nomb = intent.getBundleExtra("location_data")?.getString("nombre")?.toString()
        setContentView(R.layout.activity_map)

        createMapFragment()
    }

    private fun createMapFragment(){
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap){
        map = googleMap
        createMarkers()
    }

    private fun createMarkers() {

        //CREACION DE MARKERS EN BASE A Documentos de Firebase
        dbFirebase.collection("locations").get().addOnSuccessListener { res ->
            for (document in res){

                val latitud = document.data["latitud"]!! as Double
                val longitud = document.data["longitud"]!! as Double
                val coordenadas = LatLng(latitud, longitud)

                val nombre = document.data["nombre"]!! as String
                val newMarker = MarkerOptions().position(coordenadas).title(nombre)

                map.addMarker(newMarker)
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

        if(nomb!=null){
            val markLocation = MarkerOptions()
                .position(LatLng(latitud!!.toDouble(), longitud!!.toDouble()))
                .title("Evento: ${nomb}")
            map.addMarker(markLocation)
        }


        //-- Que haga un Zoom a un Marcador

        //Si no hay coordenadas, apuntar hacia ULIMA
        if(latitud==null&&longitud==null){
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(cooULIMA, 18f), 4000, null
        )}

        //Si hay coordenadas ingresadas, apuntar hacia estas
        else{
        map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(latitud!!.toDouble(),longitud!!.toDouble())
                    , 14f), 4000, null
            )
        }

    }

}