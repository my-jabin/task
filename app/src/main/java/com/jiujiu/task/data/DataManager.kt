package com.jiujiu.task.data

import androidx.lifecycle.LiveData
import com.jiujiu.task.data.model.Trip
import com.jiujiu.task.data.repository.TripRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor(
        private val mPreference: AppPreference,
        private val mTripRepository: TripRepository
) {

    suspend fun saveTrip(trip: Trip) = withContext(Dispatchers.IO) {
        mTripRepository.insert(trip)
    }

    fun loadAllTrips(): LiveData<List<Trip>> {
        return mTripRepository.loadAllTrips()
    }

    fun loadUnSyncTrips(): List<Trip> {
        return mTripRepository.loadUnSyncTrip()
    }


}
