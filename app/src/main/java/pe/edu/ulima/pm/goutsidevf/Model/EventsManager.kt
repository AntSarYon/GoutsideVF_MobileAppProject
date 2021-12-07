package pe.edu.ulima.pm.goutsidevf.Model

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pe.edu.ulima.pm.goutsidevf.room.EAppDatabase

class EventsManager(context: Context) {

    /*private val db = Room.databaseBuilder(
        context,
        EAppDatabase::class.java, "db_events"
    ).allowMainThreadQueries().build()*/

    private val dbFirebase = Firebase.firestore

    /*fun getEventsByRoom(callbackOK: (List<Event>) -> Unit, callbackError: (String) -> Unit){
        val events : List<Event> = db.eventDAO().findALl()
        callbackOK(events)
    }*/

    fun getProductsFirebase(
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
                        document.getTimestamp("fecha")!!.seconds,
                        document.data["image"]!! as String,
                        //(document.data["location"]!! as DocumentReference).id.toLong(),
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
    /*private fun saveIntoRoom(videogame: List<Event>) {
        videogame.forEach {
            db.eventDAO().insert(it)
        }
    }*/
}