package pe.edu.ulima.pm.goutsidevf.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class Event(

    val name: String,

    val Date : Long,

    val image : String,

    val latitud : Double,
    
    val longitud: Double
)