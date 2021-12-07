package pe.edu.ulima.pm.goutsidevf.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey()
    @ColumnInfo(name="nombre")
    val name: String,
    @ColumnInfo(name="fecha")
    val Date : Long,
    @ColumnInfo(name="image")
    val image : String,
    @ColumnInfo(name="latitud")
    val latitud : Double,
    @ColumnInfo(name="longitud")
    val longitud: Double
)