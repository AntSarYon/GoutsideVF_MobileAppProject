package pe.edu.ulima.pm.goutsidevf.Model

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LocationManager {
    private val dbFirebase = Firebase.firestore

    fun getLocationsFirebase(
        callbackOK: (List<Location>)->Unit,
        callbackError: (String)->Unit
    ){
        dbFirebase.collection("locations")
            .get().addOnSuccessListener { res ->
                val locations = arrayListOf<Location>()
                for(document in res){
                    val lc = Location(
                        document.data["nombre"]!! as String,
                        document.data["latitud"]!! as Double,
                        document.data["longitud"]!! as Double,
                        document.data["image"]!! as String,
                    )
                    locations.add(lc)
                }
                callbackOK(locations)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }
}