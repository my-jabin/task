package com.jiujiu.task.data.repository

import androidx.lifecycle.LiveData
import com.jiujiu.task.data.local.dao.TripDao
import com.jiujiu.task.data.model.Trip
import com.jiujiu.task.data.remote.RemoteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepository @Inject constructor(private val tripDao: TripDao, private val remoteService: RemoteService) : AnkoLogger {

    suspend fun insert(trip: Trip) = withContext(Dispatchers.IO) {
        val job = async {
            info { "tripDao insert start" }
            val id = tripDao.insert(trip)
            info { "tripDao insert finish. new Id= $id, trip.id = ${trip.id}" }
            trip.id = id
        }
        // waiting for the job finished
        job.await()
        info { "remote service start" }
        val result = remoteService.saveTrip(trip)
        if (result.isSuccess) {
            result.getOrNull()?.apply {
                inRemote = true
                updateLocally(this)
            }
        }
        info { "remote service finish" }
    }

    suspend fun updateLocally(trip: Trip) = withContext(Dispatchers.IO) {
        info { "tripDao update start" }
        tripDao.update(trip)
        info { "tripDao update start" }
    }

    fun loadAllTrips(): LiveData<List<Trip>> {
        return tripDao.loadAllTrips()
    }

    fun loadUnSyncTrip(): List<Trip> {
        return tripDao.loadUnSyncTrip()
    }
}
