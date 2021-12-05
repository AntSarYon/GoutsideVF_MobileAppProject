package pe.edu.ulima.pm.goutsidevf.Model

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginManager {

    companion object{
        var instance : LoginManager = LoginManager()
            private set
    }

    private val dbFirebase = Firebase.firestore

    fun saveUser(name : String, username: String, password : String,
                 callbackOK : (Long) -> Unit, callbackError: (String) ->Unit) {

        val data = hashMapOf<String, Any>(
            "name" to name,
            "username" to username,
            "password" to password
        )

        val userId = System.currentTimeMillis()

        val usersCollection = dbFirebase.collection("users")
        val newUser = usersCollection.document(userId.toString())

        newUser.set(data)
            .addOnSuccessListener {
                callbackOK(userId)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }
}