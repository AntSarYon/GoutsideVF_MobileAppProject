package pe.edu.ulima.pm.goutsidevf.room

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.ulima.pm.goutsidevf.Model.Event

@Database(entities = [Event::class], version = 1)
abstract class EAppDatabase: RoomDatabase() {
    abstract fun eventDAO() : EventDAO
}