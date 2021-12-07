package pe.edu.ulima.pm.goutsidevf.Model

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EventsManager {

    private val dbFirebase = Firebase.firestore

    fun getEventsFirebase(
        callbackOK: (List<Event>) -> Unit,
        callbackError: (String) -> Unit
    ) {
        dbFirebase.collection("events")
            .get()
            .addOnSuccessListener { res ->
                val events = arrayListOf<Event>()
                for (document in res) {
                    val ev = Event(
                        document.data["nombre"]!! as String,
                        document.getTimestamp("fecha")!!.seconds*1000,
                        document.data["image"]!! as String,
                        document.data["latitud"]!! as Double,
                        document.data["longitud"]!! as Double
                    )
//                    Log.i("evento",ev.toString())
                    events.add(ev)
                }
                callbackOK(events)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }
}