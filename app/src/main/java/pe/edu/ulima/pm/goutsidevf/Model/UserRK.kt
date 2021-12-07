package pe.edu.ulima.pm.goutsidevf.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class UserRK(

    val username: String,

    val name : String,

    val photo : String,

    val puntaje : Integer
)
