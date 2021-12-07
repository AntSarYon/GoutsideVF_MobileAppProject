package pe.edu.ulima.pm.goutsidevf.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserRK(
    @PrimaryKey()
    @ColumnInfo(name="username")
    val username: String,
    @ColumnInfo(name="name")
    val name : String,

)
