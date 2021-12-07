package pe.edu.ulima.pm.goutsidevf

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val cooULIMA = LatLng(-12.084847, -76.973097)
        val mkULIMA = MarkerOptions().position(cooULIMA).title("Universidad de Lima")

        //Añadir Marcador de la Universidad de Lima
        map.addMarker(mkULIMA)

        //Que haga un Zoom al Marcador de la ULIMA
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(cooULIMA, 18f), 4000, null
        )

    }

}