package pe.edu.ulima.pm.goutsidevf.Model

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UserRKManager(context: Context) {

    private val dbFirebase = Firebase.firestore

    fun getProductsFirebase(
        callbackOK: (List<UserRK>) -> Unit,
        callbackError: (String) -> Unit
    ){
        dbFirebase.collection("users")
            .get()
            .addOnSuccessListener { res ->
                val users = arrayListOf<UserRK>()
                for (document in res) {
                    val us = UserRK(
                        document.data["username"]!! as String,
                        document.data["name"]!! as String,
                        document.data["photo"]!! as String
                        //(document.data["location"]!! as DocumentReference).id.toLong(),
                    )
//                    Log.i("evento",ev.toString())
                    users.add(us)
                }
                callbackOK(users)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }
}