package com.jiujiu.task.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jiujiu.task.data.model.Trip

@Dao
interface TripDao {

    @Query("select * from Trip where id = :id")
    fun getTripById(id: Long): LiveData<Trip>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trip: Trip): Long

    @Delete
    fun delete(trip: Trip)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(trip: Trip)

    @Query("SELECT * FROM trip")
    fun loadAllTrips(): LiveData<List<Trip>>

    @Query("SELECT * FROM TRIP WHERE inRemote= 0 ")
    fun loadUnSyncTrip(): List<Trip>

}
