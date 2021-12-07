package pe.edu.ulima.pm.goutsidevf.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pe.edu.ulima.pm.goutsidevf.Model.Event

@Dao
interface EventDAO {
    @Query("SELECT * FROM Event")
    fun findALl(): List<Event>

    @Insert
    fun insert(event: Event)
}